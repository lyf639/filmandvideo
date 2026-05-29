import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FFmpegUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FFmpegUtils.class);
    private String ffmpegExe;

    public FFmpegUtils(String ffmpegExe) {
        this.ffmpegExe = ffmpegExe;
    }

    public void mergeVideoAndBackgroundMusic(String videoInputPath, String mp3InputPath, double seconds, String videoOutputPath) throws IOException {
        String videoNoAudio = videoInputPath + "-no-audio.mp4";
        List<String> command = Arrays.asList(this.ffmpegExe, "-i", videoInputPath, "-c:v", "copy", "-an", videoNoAudio);
        this.executeCommand(command);
        command = Arrays.asList(this.ffmpegExe, "-i", videoNoAudio, "-i", mp3InputPath, "-t", String.valueOf(seconds), "-y", videoOutputPath);
        this.executeCommand(command);
        this.deleteVideo(videoNoAudio);
        this.deleteVideo(videoInputPath);
    }

    public void createVideoThumbnail(String videoOutputPath) throws IOException {
        List<String> command = Arrays.asList(this.ffmpegExe, "-i", videoOutputPath, "-ss", "00:00:01", "-y", "-vframes", "1", videoOutputPath + ".jpg");
        this.executeCommand(command);
    }

    private void deleteVideo(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    private void executeCommand(List<String> command) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String str : command) {
            sb.append(str).append(" ");
        }
        LOGGER.info(sb.toString());
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
        }
        IOUtils.closeQuietly((Reader)bufferedReader);
        IOUtils.closeQuietly((Reader)inputStreamReader);
        IOUtils.closeQuietly((InputStream)errorStream);
    }

}
