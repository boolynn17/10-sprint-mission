package com.sprint.mission.discodeit.storage.s3;

import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class S3BinaryContentStorageTest {

    @Autowired
    private S3BinaryContentStorage storage;

    // put: S3에 업로드하고 binaryContentId 반환
    @Test
    void put_test() {
        UUID id = UUID.randomUUID();
        byte[] data = "hello s3".getBytes();

        UUID result = storage.put(id, data);

        assertThat(result).isEqualTo(id);
    }

    // get: 업로드한 파일을 InputStream으로 읽기
    @Test
    void get_test() throws Exception {
        UUID id = UUID.randomUUID();
        byte[] data = "get test content".getBytes();
        storage.put(id, data);

        InputStream result = storage.get(id);

        assertThat(result).isNotNull();
        assertThat(result.readAllBytes()).isEqualTo(data);
    }

    // download: 302 리다이렉트와 PresignedUrl 헤더를 반환
    @Test
    void download_test() {
        UUID id = UUID.randomUUID();
        storage.put(id, "download test".getBytes());

        BinaryContentDto dto = new BinaryContentDto(
                id, "test.png", 1024L, "image/png");

        ResponseEntity<Void> response = (ResponseEntity<Void>) storage.download(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);

        String location = response.getHeaders().getFirst(HttpHeaders.LOCATION);
        assertThat(location).isNotNull();
        assertThat(location).contains("binary-content/" + id);
    }
}