package com.example.pet_hospital.Service.impl;

import com.example.pet_hospital.Entity.cases;
import com.example.pet_hospital.Entity.department;
import com.example.pet_hospital.Entity.disease;
import com.example.pet_hospital.Entity.file_upload;
import com.example.pet_hospital.Mapper.DiseaseMapper;
import com.example.pet_hospital.Service.DiseaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DiseaseService_impl implements DiseaseService {

    @Autowired
    private final DiseaseMapper diseaseMapper;

    @Autowired
    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Autowired
    public DiseaseService_impl(MinioClient minioClient, DiseaseMapper diseaseMapper) {
        this.minioClient = minioClient;
        this.diseaseMapper = diseaseMapper;
    }


    @Value("${app.storage-directory}")
    private String storageDirectory;

    @PostConstruct
    public void init() {
        try {
            Path path = Paths.get(storageDirectory).toAbsolutePath().normalize();
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }


    @Override
    public void addDepartment(department k) {
        diseaseMapper.addDepartment(k);
    }

    @Override
    public department getDepartmentbyName(department k) {
        return diseaseMapper.getDepartmentbyName(k);
    }

    @Override
    public department getDepartmentbyId(String id) {
        return diseaseMapper.getDepartmentbyId(id);
    }

    @Override
    public PageInfo<department> getAllDepartment(int page, int size) {
        PageHelper.startPage(page, size);
        List<department> list = diseaseMapper.getAllDepartment();
        return new PageInfo<>(list, 5);
    }

    @Override
    public void deleteDepartment(department k) {
        diseaseMapper.deleteDepartment(k);
    }

    @Override
    public void changeDepartment(department k) {
        diseaseMapper.changeDepartment(k);
    }

    @Override
    public void addDisease(disease d) {
        diseaseMapper.addDisease(d);
    }

    @Override
    public disease getDiseasebyName(disease d) {
        return diseaseMapper.getDiseasebyName(d);
    }

    @Override
    public void deleteDisease(disease d) {
        diseaseMapper.deleteDisease(d);
    }

    @Override
    public void changeDiseaseName(disease d) {
        diseaseMapper.changeDiseaseName(d);
    }

    @Override
    public disease getDiseasebyId(String disease_id) {
        return diseaseMapper.getDiseasebyId(disease_id);
    }

    @Override
    public PageInfo<disease> getDiseasebyDepartment(String department_id, int page, int size) {
        PageHelper.startPage(page, size);
        List<disease> list = diseaseMapper.getDiseasebyDepartment(department_id);
        return new PageInfo<>(list, 5);
    }

    @Override
    public cases[] getCasebyDis(String disease_id) {
        return diseaseMapper.getCasebyDis(disease_id);
    }

    @Override
    public void addCase(cases i) {
        diseaseMapper.addCase(i);
    }

    @Override
    public cases getCasebyName(String case_name) {
        return diseaseMapper.getCasebyName(case_name);
    }

    @Override
    public void deleteCase(cases i) {
        diseaseMapper.deleteCase(i);
    }

    @Override
    public void changeCase(cases i) {
        diseaseMapper.changeCase(i);
    }

    @Override
    public cases getCasebyId(String case_id) {
        return diseaseMapper.getCasebyId(case_id);
    }

    @Override
    public PageInfo<cases> searchCase(int page, int size, String case_name) {
        PageHelper.startPage(page, size);
        List<cases> list = diseaseMapper.searchCase(case_name);
        return new PageInfo<>(list, 5);
    }

    @Override
    public PageInfo<cases> findPaginated(int page, int size) {
        PageHelper.startPage(page, size);
        List<cases> list = diseaseMapper.CaseList();
        //Page<case_base> pageList = (Page<case_base>) list;
        //System.out.println(pageInfo);
        //return new PageResult<>(list, pageInfo.getTotal(), pageInfo.getPages(), page, size);
        //return new PageResult<>(case_bases, case_bases.getTotal(), case_bases.getPages(), page, size);
        return new PageInfo<>(list, 5);
    }

    @Override
    public List<department> findAllDepartments() {
        return diseaseMapper.findAllDepartments();
    }


    //以下是media的操作


    @Override
    public String uploadMedia(cases m) throws Exception {
        MultipartFile file = m.getFile();
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );

        cases newMedia = new cases();
        newMedia.setCase_id(m.getCase_id()); // 设置你的case_id
        newMedia.setMedia_name(fileName);
        newMedia.setMedia_type(file.getContentType().startsWith("image/") ? "image" : "video");
        newMedia.setMedia_url(url);
        newMedia.setCategory(m.getCategory()); // 设置category
        diseaseMapper.addMedia(newMedia);

        return url;
    }

    @Override
    public void deleteMedia(String mediaId) throws Exception {
        cases mediaToDelete = diseaseMapper.getMediabyId(mediaId);
        if (mediaToDelete != null) {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(mediaToDelete.getMedia_name())
                            .build()
            );
            diseaseMapper.deleteMedia(mediaToDelete);
        } else {
            throw new Exception("Media not found with id: " + mediaId);
        }
    }

    @Override
    public String getMediabyId(String mediaId) {
        cases media = diseaseMapper.getMediabyId(mediaId);
        return media != null ? media.getMedia_url() : null;
    }

    @Override
    public void changeMedia(cases m) throws Exception {
        //先删除minio中的文件
        cases mediaToDelete = diseaseMapper.getMediabyId(m.getMedia_id());
        if (mediaToDelete != null) {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(mediaToDelete.getMedia_name())
                            .build()
            );
        }

        //再上传新文件
        MultipartFile file = m.getFile();
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        //更新url
        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
        //更新数据库
        cases newMedia = new cases();
        newMedia.setMedia_id(m.getMedia_id());
        //更新这个id的media的url
        newMedia.setMedia_url(url);
        newMedia.setMedia_name(fileName);
        newMedia.setMedia_type(file.getContentType().startsWith("image/") ? "image" : "video");
        //只更新上面这三个字段，其他字段不变
        diseaseMapper.changeMedia(newMedia);

    }

    @Override
    public Path getFilePath(String filename) {
        return Paths.get(storageDirectory).resolve(filename).toAbsolutePath().normalize();
    }

    @Override
    public File mergeFiles(File[] files, String outputFileName) throws IOException {
        File mergedFile = new File(storageDirectory, outputFileName);
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(mergedFile))) {
            for (File file : files) {
                Files.copy(file.toPath(), out);
                file.delete(); // Delete after merge
            }
        }
        return mergedFile;
    }

    @Override
    public boolean allChunksExist(String baseFilename, int totalChunks) {
        for (int i = 0; i < totalChunks; i++) {
            if (!new File(storageDirectory, baseFilename + "_" + i).exists()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateUploadProgress(String uploadId, int chunk) {
        file_upload upload = diseaseMapper.getUploadbyId(uploadId);
        upload.setUploaded_chunks(upload.getUploaded_chunks() + 1);
        diseaseMapper.updateUpload(upload);
    }

    @Override
    public boolean isUploadComplete(String uploadId) {
        return false;
    }

    @Override
    public void uploadCompletedMedia(cases m, File mergedFile, String type) {
        String fileName = System.currentTimeMillis() + "_" + mergedFile.getName();
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(new FileInputStream(mergedFile), mergedFile.length(), -1)
                            .contentType(type)
                            .build()
            );
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
            cases newMedia = new cases();
            newMedia.setCase_id(m.getCase_id());
            newMedia.setMedia_name(fileName);
            newMedia.setMedia_type(type);
            newMedia.setMedia_url(url);
            newMedia.setCategory(m.getCategory());
            diseaseMapper.addMedia(newMedia);
            // 尝试删除文件并检查是否成功
            boolean isDeleted = mergedFile.delete();
            if (!isDeleted) {
                // 日志记录或处理未能删除文件的情况
                System.out.println("Failed to delete the file: " + mergedFile.getAbsolutePath());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public cases getMediabyUrl(String mediaUrl) {
        return diseaseMapper.getMediabyUrl(mediaUrl);
    }

    @Override
    public cases getMediabyName(String mediaName) {
        return diseaseMapper.getMediabyName(mediaName);
    }

    @Override
    public cases[] findAllMedia() {
        return diseaseMapper.findAllMedia();
    }

    @Override
    public cases[] getMediaByCaseId(String caseId) {
        return diseaseMapper.getMediaByCaseId(caseId);
    }

    @Override
    public cases[] getMediaByType(String mediaType) {
        return diseaseMapper.getMediaByType(mediaType);
    }

    @Override
    public cases[] getMediaByCategory(String category) {
        return diseaseMapper.getMediaByCategory(category);
    }

    @Override
    public cases[] getMediaByCaseIdAndType(String caseId, String mediaType) {
        return diseaseMapper.getMediaByCaseIdAndType(caseId, mediaType);
    }

    @Override
    public cases[] getMediaByCaseIdAndCategory(String caseId, String category) {
        return diseaseMapper.getMediaByCaseIdAndCategory(caseId, category);
    }

    @Override
    public cases[] getMediaByTypeAndCategory(String mediaType, String category) {
        return diseaseMapper.getMediaByTypeAndCategory(mediaType, category);
    }

    @Override
    public cases[] getMediaByCaseIdAndTypeAndCategory(String caseId, String mediaType, String category) {
        return diseaseMapper.getMediaByCaseIdAndTypeAndCategory(caseId, mediaType, category);
    }


}
