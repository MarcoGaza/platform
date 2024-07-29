package cn.econets.blossom.framework.excel.core.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Excel Tools
 *
 */
public class ExcelUtils {

    /**
     * Change the list to Excel Response to the front end
     *
     * @param response Response
     * @param filename File name
     * @param sheetName Excel sheet Name
     * @param head Excel head Head
     * @param data Data list
     * @param <T> Generic，Guarantee head Japanese data Type consistency
     * @throws IOException Write failed
     */
    public static <T> void write(HttpServletResponse response, String filename, String sheetName,
                                 Class<T> head, List<T> data) throws IOException {
        // Output Excel
        EasyExcel.write(response.getOutputStream(), head)
                .autoCloseStream(false) // Do not close automatically，Leave it to Servlet Handle it yourself
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // Based on column Length，Automatic adaptation。Maximum 255 Width
                .sheet(sheetName).doWrite(data);
        // Settings header Japanese contentType。The reason for writing it at the end is，Avoid error reporting，Response contentType has been modified
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    }

    public static <T> List<T> read(MultipartFile file, Class<T> head) throws IOException {
       return EasyExcel.read(file.getInputStream(), head, null)
                .autoCloseStream(false)  // Do not close automatically，Leave it to Servlet Handle it yourself
                .doReadAllSync();
    }

}
