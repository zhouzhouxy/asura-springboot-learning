## springboot-minio

Spring Boot application demonstrating file upload to MinIO and deletion, with a simple Thymeleaf UI.

### How to run

1. Configure environment in `springboot-minio/src/main/resources/application-test.yml` or your active profile.
2. Ensure a MinIO server is reachable and credentials are correct.
3. From the repository root, run:

```bash
mvn -pl springboot-minio -am spring-boot:run
```

The server defaults to port 9091 (see `application.yml`).

### Configuration

Files:

- `springboot-minio/src/main/resources/application.yml`
- `springboot-minio/src/main/resources/application-test.yml`

Properties:

- `minio.url`: Base URL to access objects (e.g., `http://localhost:9000/` or CDN URL used to construct returned links)
- `minio.access.name`: Access key
- `minio.access.secret`: Secret key
- `minio.bucket.name`: Target bucket name

### Public API (HTTP)

- `GET /` — Render upload form (Thymeleaf `uploadForm.html`)
  - Returns: HTML page with form and optional preview image

- `POST /upload` — Upload a file to MinIO
  - Request: multipart form-data with field `file`
  - Response: HTML page with message and `imgUrl` if successful

- `GET /delete/{fileName}` — Delete an object by name from the bucket
  - Path variable: `fileName` (object key)
  - Response: HTML page with status message

### Service Layer

Class: `com.asura.minio.service.FileStorageService`

- `void uploadFile(MultipartFile file)`
  - Uploads the provided file to `minio.bucket.name` using original filename.

- `String uploadFileReturnUrl(MultipartFile file)`
  - Uploads to a date-based folder `yyyy/MM/dd/` with a random 20-character name and preserves extension.
  - Returns a public URL constructed as `minio.url + bucket + "/" + objectName`.

- `void deleteFile(String fileName)`
  - Deletes object with key `fileName` from `minio.bucket.name`.

- `static String getFileExtension(String fileName)`
  - Returns extension; special handling for `*.tar.gz`.

- `static String generateRandomString()`
  - Returns random alphanumeric string of length 20.

### Controller

Class: `com.asura.minio.controller.FileController`

- `ModelAndView index()` — maps `GET /`, returns `uploadForm` view.
- `ModelAndView uploadFile(MultipartFile file)` — maps `POST /upload`, uses `uploadFileReturnUrl`, returns view with `message` and `imgUrl`.
- `ModelAndView deleteFile(String fileName)` — maps `GET /delete/{fileName}`, deletes file, returns view with `message`.

### UI Template

File: `springboot-minio/src/main/resources/templates/uploadForm.html`

- Multipart form posting to `/upload` with `file` field
- Displays `message` and preview image if `imgUrl` is present

### Examples

Using `curl` to upload a file (simulate the form submission):

```bash
curl -F "file=@/path/to/image.png" http://localhost:9091/upload
```

Delete an uploaded object:

```bash
curl http://localhost:9091/delete/2025/01/01/AbCdEfGhIjKlMnOpQrSt.png
```

Use `FileStorageService` directly in code:

```java
@Autowired
private FileStorageService fileStorageService;

public String upload(MultipartFile file) {
    return fileStorageService.uploadFileReturnUrl(file);
}
```

### Bootstrapping

Entry point: `com.asura.minio.MinioApp` (`@SpringBootApplication`)

Run via IDE or:

```bash
mvn -pl springboot-minio -am spring-boot:run
```

