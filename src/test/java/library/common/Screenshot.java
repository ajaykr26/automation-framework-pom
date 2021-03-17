package library.common;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static library.selenium.BaseTest.reportPath;

public class Screenshot {

    public static String getScreenshotPath() {
        return reportPath + "/screenshots/" + TestContext.getInstance().testdataGet("fw.testname");
    }

    public static void insertImageToWord(Boolean flag) throws IOException, InvalidFormatException {
        if (flag) {
            final Stream<Path> fileStream = Files.list(Paths.get(getScreenshotPath()));
            List<File> files = fileStream
                    .map(Path::toFile)
                    .sorted(Comparator.comparing(File::lastModified))
                    .collect(Collectors.toList());
            XWPFDocument doc = new XWPFDocument();
            XWPFParagraph p = doc.createParagraph();
            XWPFRun xwpfRun = p.createRun();

            for (File file : files) {
                int format = XWPFDocument.PICTURE_TYPE_PNG;
                p.setAlignment(ParagraphAlignment.CENTER);
                xwpfRun.addBreak();
                xwpfRun.addPicture(new FileInputStream(file), format, file.getName(), Units.toEMU(480), Units.toEMU(240));
            }
            FileOutputStream out = new FileOutputStream(getScreenshotPath() + File.separator + TestContext.getInstance().testdataGet("fw.testname") + ".doc");
            doc.write(out);
            out.close();
        }
    }
}
