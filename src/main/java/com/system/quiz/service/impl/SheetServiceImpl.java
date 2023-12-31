package com.system.quiz.service.impl;


import com.system.quiz.dao.impl.QuestionDAOImpl;
import com.system.quiz.dao.impl.SheetDAOImpl;
import com.system.quiz.dao.impl.TestDAOImpl;
import com.system.quiz.entity.*;
import com.system.quiz.service.SheetService;
import jakarta.transaction.Transactional;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SheetServiceImpl implements SheetService {


    private SheetDAOImpl sheetDAOImpl;
    private TestDAOImpl testDAOImpl;
    private QuestionDAOImpl questionDAOImpl;
    private QuestionServiceImpl questionServiceImpl;
    private static final Logger logger = LoggerFactory.getLogger(SheetServiceImpl.class);

    @Autowired
    public SheetServiceImpl(SheetDAOImpl sheetDAOImpl, TestDAOImpl testDAOImpl, QuestionServiceImpl questionServiceImpl, QuestionDAOImpl questionDAOImpl){
        this.sheetDAOImpl = sheetDAOImpl;
        this.testDAOImpl = testDAOImpl;
        this.questionDAOImpl = questionDAOImpl;
        this.questionServiceImpl = questionServiceImpl;
    }
    @Override
    public List<SheetDTO> getSheetDTOListByStudentId(int studentId) {

        return sheetDAOImpl.getSheetDTOListByStudentId(studentId);
    }

    @Override
    public Sheet getSheetByTestIdAndStudentId(int testId, int studentId) {

        return sheetDAOImpl.getSheetByTestIdAndStudentId(testId, studentId);
    }

    @Override
    @Transactional
    public Answer saveSheetQuestionAnswer(int questionId, int sheetId, Answer answer) {
        return sheetDAOImpl.saveSheetQuestionAnswer(questionId, sheetId, answer);
    }

    @Override
    @Transactional
    public SheetDTO submitTestSheet(Sheet sheet, Timestamp startTime) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        if(sheet.getSubmitTime() == null){
            sheet.setSubmitTime(currentTimestamp);
            sheet.setSubmited(true);
            sheet.setTakeTimes(1);
        }else{
            sheet.setSubmitTime(currentTimestamp);
            sheet.setSubmited(true);
            Integer times = sheet.getTakeTimes();
            if(times !=null){
                sheet.setTakeTimes(++times);
            }
        }
//        sheet.setStartTime(startTime);

        //auto mark step
        double correctnessRate = autoMarkSheetStep(sheet);

        SheetDTO sheetDTO = sheetDAOImpl.submitTestSheet(sheet);


        if(sheet.getTest().getAnswerShowModel().trim().equalsIgnoreCase("Immediately")){
            sheetDTO.setCorrectnessRate(correctnessRate);
        }

        return sheetDTO;
    }

    private double autoMarkSheetStep(Sheet sheet) {
        List<Question> questions = sheet.getTest().getQuestions();

        int totalCorrectOptions = 0; // Initialize the total correct options count
        int totalChoices = 0; // Initialize the total choices count
        int sheetScore = 0;

        for (Question question : questions) {
            Integer questionId = question.getId();
            Answer answer = questionDAOImpl.getAnswerByQuestionIdAndSheetId(questionId, sheet.getId());
            if(answer == null ){
                break;
            }
            questionServiceImpl.markAnswer(question, answer);
            sheetScore += answer.getScore();

            // Check if the question is a Multiple choice question
            if ("Multiple".equalsIgnoreCase(question.getType())) {
                totalCorrectOptions += countCorrectChoices(question.getAnswer(), answer.getContext());
                // Count the total number of choices (including blanks)
                totalChoices += countChoices(question.getChoice());
            } else {
                // For other question types, consider the question as having only one correct option
                totalCorrectOptions += answer.getScore() > 0 ? 1 : 0;
                // For other question types, consider the question as having only one choice
                totalChoices += 1;
            }

        }

        sheet.setScore(sheetScore);

        // Calculate the Correctness Rate as the total number of correct options divided by the total number of choices (including blanks).
        double correctnessRate = (double) totalCorrectOptions / totalChoices;

        return correctnessRate;
    }

    private int countCorrectChoices(String questionAnswer, String answerContext) {
        String[] correctChoices = questionAnswer.split(",");
        String[] selectedChoices = answerContext.split(",");

        int correctCount = 0;

        for (String choice : selectedChoices) {
            if (containsIgnoreCase(correctChoices, choice.trim())) {
                correctCount++;
            }
        }

        return correctCount;
    }

    private int countChoices(String choices) {
        String[] allChoices = choices.split(",");
        return allChoices.length;
    }

    private boolean containsIgnoreCase(String[] array, String value) {
        for (String item : array) {
            if (item.trim().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public List<MarkItemDTO> getMarkListByStudentId(int studentId) {
        return sheetDAOImpl.getMarkListByStudentId(studentId);
    }




    @Override
    public Sheet getSheetById(int sheetId) {
        return sheetDAOImpl.getSheetById(sheetId);
    }


    @Override
    @Transactional
    public SheetDTO getSheetDTOById(int sheetId) {
        return sheetDAOImpl.getSheetDTOById(sheetId);
    }



    @Override
    @Transactional
    /**
     * calculate face similarity
     */
    public void saveSheet(Sheet sheet) {
        // Using openCV check face similarity
        byte[] avatar = sheet.getStudent().getUser().getAvatar();
        byte[] photo = sheet.getPhoto();

        // Standard size for resized face images
        Size targetSize = new Size(100, 100);


        // Load the cascade classifier for face detection
        CascadeClassifier faceCascade = new CascadeClassifier("/Users/weinanwang/develop/code/backend/src/main/resources/haarcascade_frontalface_default.xml");

        // Convert avatar and photo bytes to Mat (OpenCV matrix)
        Mat avatarMat = Imgcodecs.imdecode(new MatOfByte(avatar), Imgcodecs.IMREAD_COLOR);
        Mat photoMat = Imgcodecs.imdecode(new MatOfByte(photo), Imgcodecs.IMREAD_COLOR);

        // Resize images to the same dimensions
        Imgproc.resize(avatarMat, avatarMat, targetSize);
        Imgproc.resize(photoMat, photoMat, targetSize);

        // Detect faces in the images
        MatOfRect avatarFaces = new MatOfRect();
        MatOfRect photoFaces = new MatOfRect();
        faceCascade.detectMultiScale(avatarMat, avatarFaces);
        faceCascade.detectMultiScale(photoMat, photoFaces);

        // Check if both images have detected faces
        if (!avatarFaces.empty() && !photoFaces.empty()) {
            // Compare the first detected face in each image (assuming there's only one face per image)
            Rect avatarFace = avatarFaces.toArray()[0];
            Rect photoFace = photoFaces.toArray()[0];

            // Calculate the similarity score
            double similarityScore = calculateSimilarity(avatarMat.submat(avatarFace), photoMat.submat(photoFace));

            logger.info("face similarity"+similarityScore);

            // set similarity in sheet
            sheet.setSimilarity(similarityScore);

        } else {
            // Faces not detected in one or both images, handle accordingly
            logger.info("not capture face from picture");
            //set a 0 score in sheet
            sheet.setSimilarity(0.0);
        }
        sheetDAOImpl.updateSheet(sheet);

    }


    @Override
    public Sheet getMarkSheetByTestIdAndStudentId(int testId, int studentId) {

        return sheetDAOImpl.getMarkSheetByTestIdAndStudentId(testId, studentId);
    }

    @Override
    public List<MarkItemDTO> getMarkListByTestId(int testId) {
        return sheetDAOImpl.getMarkListByTestId(testId);
    }

    /**
     * if the testId if empty get all testId of the teacherId
     * @param teacherId
     * @param testId
     * @return
     */
    @Override
    public List<MarkItemDTO> getMarkListByTeacherIdOrTestId(int teacherId, int testId) {
        if( testId == -1 || testId == 0 || testId < 0){
           return sheetDAOImpl.getMarkListByTeacherId(teacherId);
        }else {
            return sheetDAOImpl.getMarkListByTestId(testId);
        }

    }

    @Override
    public MarkDTO getMarkDTOBySheetId(int sheetId) {
        return sheetDAOImpl.getMarkDTOBySheetId(sheetId);
    }

    @Override
    @Transactional
    public void postMark(Sheet sheet, int isAnonymous) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        sheet.setUpdateTime(currentTimestamp);
         sheet.setMarked(true);
         sheet.setAnonymous(isAnonymous);
         sheetDAOImpl.updateSheet(sheet);
    }

    @Override
    public FaceCompareDTO getPhotoCompare(Sheet sheet) {
        byte[] avatar = sheet.getStudent().getUser().getAvatar();
        byte[] photo = sheet.getPhoto();
        FaceCompareDTO faceCompareDTO = new FaceCompareDTO();
        faceCompareDTO.setPhoto(photo);
        faceCompareDTO.setAvatar(avatar);
        return faceCompareDTO;
    }

    @Override
    @Transactional
    public void saveStartTime(Sheet sheet, Timestamp startTime) {
        if(sheet.getStartTime() == null){
            sheet.setOpenTimes(1);
            sheet.setStartTime(startTime);
        }else{
            Integer times = sheet.getOpenTimes();
            sheet.setOpenTimes(++times);
        }

        sheetDAOImpl.updateSheet(sheet);

    }

    @Override
    @Transactional
    public void updateSheet(Sheet sheet) {
        sheetDAOImpl.updateSheet(sheet);
    }


    private double calculateSimilarity(Mat face1, Mat face2) {

        // Calculate SSIM using JAMA library
        double ssimScore = calculateSSIM(face1, face2);

        // Normalize SSIM score to be between 0 and 1 (1 indicates perfect similarity)
        double normalizedScore = (ssimScore + 1.0) / 2.0;

        return normalizedScore;
    }


    private static double calculateSSIM(Mat image1, Mat image2) {
        // Convert images to grayscale if they are not already
        Mat grayImage1 = new Mat();
        Mat grayImage2 = new Mat();
        Imgproc.cvtColor(image1, grayImage1, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(image2, grayImage2, Imgproc.COLOR_BGR2GRAY);

        Size targetSize = new Size(100, 100);
        Imgproc.resize(grayImage1, grayImage1, targetSize);
        Imgproc.resize(grayImage2, grayImage2, targetSize);

        double c1 = Math.pow(0.01 * 255, 2);
        double c2 = Math.pow(0.03 * 255, 2);

        double mean1 = computeMean(grayImage1);
        double mean2 = computeMean(grayImage2);
        double variance1 = computeVariance(grayImage1, mean1);
        double variance2 = computeVariance(grayImage2, mean2);



        double covariance = computeCovariance(grayImage1, grayImage2, mean1, mean2);

        double luminance = (2 * mean1 * mean2 + c1) / (Math.pow(mean1, 2) + Math.pow(mean2, 2) + c1);
        double contrast = (2 * Math.sqrt(variance1) * Math.sqrt(variance2) + c2) / (variance1 + variance2 + c2);
        double structure = (covariance + c2 / 2) / (Math.sqrt(variance1) * Math.sqrt(variance2) + c2 / 2);

        // Calculate SSIM score
        double ssim = luminance * contrast * structure;

        return ssim;
    }


    private static double computeMean(Mat image) {
        double sum = 0;
        int totalPixels = image.rows() * image.cols();

        for (int row = 0; row < image.rows(); row++) {
            for (int col = 0; col < image.cols(); col++) {
                double pixelValue = image.get(row, col)[0];
                sum += pixelValue;
            }
        }

        return sum / totalPixels;
    }

    private static double computeVariance(Mat image, double mean) {
        double sumSquaredDifferences = 0;
        int totalPixels = image.rows() * image.cols();

        for (int row = 0; row < image.rows(); row++) {
            for (int col = 0; col < image.cols(); col++) {
                double pixelValue = image.get(row, col)[0];
                double difference = pixelValue - mean;
                sumSquaredDifferences += difference * difference;
            }
        }

        return sumSquaredDifferences / totalPixels;
    }

    private static double computeCovariance(Mat image1, Mat image2, double mean1, double mean2) {
        if (image1.size().equals(image2.size())) {
            double sumCovariance = 0;
            int totalPixels = image1.rows() * image1.cols();

            for (int row = 0; row < image1.rows(); row++) {
                for (int col = 0; col < image1.cols(); col++) {
                    double pixelValue1 = image1.get(row, col)[0];
                    double pixelValue2 = image2.get(row, col)[0];
                    double diff1 = pixelValue1 - mean1;
                    double diff2 = pixelValue2 - mean2;
                    sumCovariance += diff1 * diff2;
                }
            }

            return sumCovariance / totalPixels;
        } else {
            throw new IllegalArgumentException("Images must have the same dimensions for covariance calculation.");
        }
    }





}
