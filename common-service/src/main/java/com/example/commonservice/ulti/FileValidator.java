package com.example.commonservice.ulti;

import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileValidator {

	// Danh sách extension cho phép
	private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
			"jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "xls", "xlsx", "txt"
	);

	// Danh sách MediaType cho phép
	private static final Set<MediaType> ALLOWED_MEDIA_TYPES = Set.of(
			MediaType.IMAGE_JPEG,
			MediaType.IMAGE_PNG,
			MediaType.IMAGE_GIF,
			MediaType.APPLICATION_PDF,
			MediaType.valueOf("application/msword"),
			MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
			MediaType.valueOf("application/vnd.ms-excel"),
			MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
			MediaType.TEXT_PLAIN
	);

	/**
	 * Validate danh sách files
	 */
	public static List<String> validateFiles(List<MultipartFile> files) {
		List<String> errors = new ArrayList<>();

		if (files == null || files.isEmpty()) {
			errors.add("Danh sách file không được trống");
			return errors;
		}

		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			errors.addAll(validateFile(file, i));
		}

		return errors;
	}

	/**
	 * Validate từng file
	 */
	private static List<String> validateFile(MultipartFile file, int index) {
		List<String> errors = new ArrayList<>();

		// Check file trống
		if (file.isEmpty()) {
			errors.add("File thứ " + (index + 1) + " bị trống");
			return errors;
		}

		String originalFilename = file.getOriginalFilename();

		// Check filename
		if (!StringUtils.hasText(originalFilename)) {
			errors.add("File thứ " + (index + 1) + " không có tên");
			return errors;
		}

		// Validate extension
		String extensionError = validateExtension(originalFilename, index);
		if (extensionError != null) {
			errors.add(extensionError);
		}

		// Validate content-type
		String contentTypeError = validateContentType(file, originalFilename, index);
		if (contentTypeError != null) {
			errors.add(contentTypeError);
		}

		return errors;
	}

	/**
	 * Validate extension
	 */
	private static String validateExtension(String filename, int index) {
		String extension = StringUtils.getFilenameExtension(filename);

		if (extension == null) {
			return "File thứ " + (index + 1) + " không có extension: " + filename;
		}

		if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
			return "File thứ " + (index + 1) + " có extension không hợp lệ: " + extension +
			       ". Chỉ chấp nhận: " + String.join(", ", ALLOWED_EXTENSIONS);
		}

		return null;
	}

	/**
	 * Validate content-type sử dụng Spring MediaTypeFactory
	 */
	private static String validateContentType(MultipartFile file, String filename, int index) {
		try {
			// Lấy content-type từ file name (Spring utility)
			MediaType mediaTypeFromName = MediaTypeFactory.getMediaType(filename)
					.orElse(MediaType.APPLICATION_OCTET_STREAM);

			// Lấy content-type từ client
			String clientContentType = file.getContentType();
			MediaType mediaTypeFromClient = clientContentType != null ?
					MediaType.parseMediaType(clientContentType) : MediaType.APPLICATION_OCTET_STREAM;

			// So sánh cả hai
			if (isMediaTypeAllowed(mediaTypeFromName)) {
				return "File thứ " + (index + 1) + " có type không hợp lệ (theo tên): " +
				       mediaTypeFromName + ". Chỉ chấp nhận: " + getAllowedTypesString();
			}

			if (isMediaTypeAllowed(mediaTypeFromClient)) {
				return "File thứ " + (index + 1) + " có type không hợp lệ (theo client): " +
				       mediaTypeFromClient + ". Chỉ chấp nhận: " + getAllowedTypesString();
			}

			// Kiểm tra client content-type và extension-based content-type
			if (!mediaTypeFromName.equals(mediaTypeFromClient)) {
				return "File thứ " + (index + 1) + " có content-type không khớp: " +
				       "Client: " + mediaTypeFromClient + ", Expected: " + mediaTypeFromName;
			}

		} catch (Exception e) {
			return "Lỗi khi validate file thứ " + (index + 1) + ": " + e.getMessage();
		}

		return null;
	}

	/**
	 * Kiểm tra MediaType có được phép không
	 */
	private static boolean isMediaTypeAllowed(MediaType mediaType) {
		return ALLOWED_MEDIA_TYPES.stream()
				.noneMatch(allowedType -> allowedType.includes(mediaType));
	}

	/**
	 * Lấy danh sách MediaType được phép dưới dạng string
	 */
	private static String getAllowedTypesString() {
		return ALLOWED_MEDIA_TYPES.stream()
				.map(MediaType::toString)
				.collect(java.util.stream.Collectors.joining(", "));
	}

	/**
	 * Validate file size
	 */
	public static String validateFileSize(MultipartFile file, int index, long maxSizeBytes) {
		if (file.getSize() > maxSizeBytes) {
			return "File thứ " + (index + 1) + " vượt quá kích thước cho phép: " +
			       formatFileSize(file.getSize()) + " (Tối đa: " + formatFileSize(maxSizeBytes) + ")";
		}
		return null;
	}

	private static String formatFileSize(long bytes) {
		if (bytes < 1024) {
			return bytes + " bytes";
		}
		if (bytes < 1024 * 1024) {
			return (bytes / 1024) + " KB";
		}
		return (bytes / (1024 * 1024)) + " MB";
	}
}
