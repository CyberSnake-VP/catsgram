package example.services;

import example.dal.ImageRepository;
import example.dal.PostRepository;
import example.dto.response.image.ImageData;
import example.dto.response.image.ImageResponse;
import example.exception.ImageFileException;
import example.exception.NotFoundException;
import example.mapper.ImageMapper;
import example.model.Image;
import example.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;
    private static final String IMAGE_SAVE_DIRECTORY = "src/main/resources/post-image-directory";
    private static final String FILE_SAVE_ERROR = "Ошибка при сохранении файла";
    private static final String POST_NOT_FOUND = "Пост не найден";
    private static final String IMAGE_READ_EXCEPTION = "Ошибка при загрузке файла";
    private static final String FILE_NOT_FOUND = "Файл не найден";
    private static final String IMAGE_NOT_FOUND = "Картина(видео-файл) не найден(a)";



    public List<ImageResponse> getPostImages(Long postId) {
        return imageRepository.findAll(postId).stream()
                .map(ImageMapper::toImageResponse)
                .toList();
    }


    /// ЛОГИКА ПО СОХРАНЕНИЮ КАРТИНКИ
    // ОСНОВНОЙ МЕТОД ДЛЯ СОХРАНЕНИЯ КАРТИНКИ В ФАЙЛ, ВОЗВРАЩАЕТ ПУТЬ!
    private Path saveFile(MultipartFile file, Post post) {
        /**
         * 1. Создаем уникальное имя файла с помощью временной метки и расширения файла
         *  используем спец метод класса StringUtils(getFIlenameExtension(..)), чтобы достать расширение
         * 2. Создаем путь с директорией автора id, и поста id.
         * Соединяем пути.
         * Итоговый путь: post-image-directory/id-Автора/id-Поста/ 134543454434.mp4
         * Так мы добьемся уникальности имен, и удобную раскладку хранения
         * */
        try {

            // выполнили 1 пункт
            String uniqueFileName = String.format("%d.%s", Instant.now().toEpochMilli(),
                    StringUtils.getFilenameExtension(file.getOriginalFilename()));

            // выполнили 2 пункт
            Path uploadDirectory = Paths.get(IMAGE_SAVE_DIRECTORY, post.getAuthorId().toString(), post.getId().toString());

            Path filePath = uploadDirectory.resolve(uniqueFileName);

            if (!Files.exists(filePath)) {
                Files.createDirectories(uploadDirectory);
            }

            file.transferTo(filePath);
            return filePath;

        } catch (IOException e) {
            throw new ImageFileException(FILE_SAVE_ERROR);
        }

    }

    // МАППИНГ И СОХРАНЕНИЕ В БД + ВАЛИДАЦИЯ
    private Image saveImage(MultipartFile file, Long postId) {

        // ищем пост в бд
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new NotFoundException(POST_NOT_FOUND));

        Path path = saveFile(file, post);

        Image image = new Image();
        image.setPostId(postId);
        image.setOriginalName(file.getOriginalFilename());
        image.setFilePath(path.toString());

        return imageRepository.save(image);
    }

    // МЕТОД ДЛЯ КОНТРОЛЛЕРА СОХРАНЕНИЯ МНОЖЕСТВА КАРТИНОК ЧЕРЕЗ STREAM
    public List<ImageResponse> saveImages(List<MultipartFile> files, Long postId) {
        return files.stream()
                .map(file -> saveImage(file, postId))
                .map(ImageMapper::toImageResponse)
                .toList();
    }


    /// МЕТОД ДЛЯ ВЫГРУЗКИ ФАЙЛА(КАРТИНКИ)
    private byte[] loadFile(Image image) {
        Path path = Paths.get(image.getFilePath());
        if(Files.exists(path)) {
            try{
                return Files.readAllBytes(path);
            }catch (IOException e) {
                throw new ImageFileException(IMAGE_READ_EXCEPTION + " id: " + image.getId());
            }
        }
        throw new ImageFileException(FILE_NOT_FOUND + " id: " + image.getId());
    }

    // МЕТОД КОНТРОЛЛЕРА ДЛЯ ЗАГРУЗКИ КАРТИНКИ
    public ImageData getImageDate(Long id) {
        Image image = imageRepository.findOne(id)
                .orElseThrow(() -> new NotFoundException(IMAGE_NOT_FOUND));
        byte[] imageDate = loadFile(image);
        return new ImageData(imageDate, image.getOriginalName());
    }


}
