package site.zhongkai.ask.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.zhongkai.ask.entity.ExamInfo;
import site.zhongkai.ask.service.IExamInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

@Log4j2
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Resource
    private IExamInfoService examInfoService;

    /**
     * 上传excel到题库基本信息表 IO流形式
     *
     * @param file 上传的文件名
     */
    @PostMapping(value = "/excel_to_exam_info_by_io")
    public Map<String, Object> uploadExcelToExamInfoByIO(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<ExamInfo> list = new ArrayList<>();       //正常可插入数据
        List<ExamInfo> repeatList = new ArrayList<>();
        //数据库中重复数据
        try {
            InputStream is = file.getInputStream();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            ExamInfo examinfo;
            Date loadDate = new Date();
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        examinfo = new ExamInfo();
                        //1.读取excel中相关数据
                        HSSFCell examtypeid = hssfRow.getCell(0);
                        HSSFCell name = hssfRow.getCell(1);
                        HSSFCell a = hssfRow.getCell(2);
                        HSSFCell b = hssfRow.getCell(3);
                        HSSFCell c = hssfRow.getCell(4);
                        HSSFCell d = hssfRow.getCell(5);
                        HSSFCell e = hssfRow.getCell(6);
                        HSSFCell correctanswer = hssfRow.getCell(7);
                        HSSFCell explaininfo = hssfRow.getCell(8);

                        //封装题库基本信息字段
                        examinfo.setExamTypeId((getValue(examtypeid))).setExamName(getValue(name)).setOptionA(getValue(a)).setOptionB(getValue(b)).setCorrectAnswer(getValue(correctanswer)).setCreateTime(loadDate);
                        if (c != null) examinfo.setOptionC(getValue(c));
                        if (d != null) examinfo.setOptionD(getValue(d));
                        if (e != null) examinfo.setOptionE(getValue(e));
                        if (explaininfo != null) examinfo.setExamExplain(getValue(explaininfo));
                        //判断数据是否在数据库中重复
                        List<ExamInfo> l = examInfoService.selectList(new EntityWrapper<ExamInfo>().eq("exam_name", examinfo.getExamName()));
                        if (l.size() > 0) {
                            repeatList.add(examinfo);
                        } else {
                            list.add(examinfo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("repeatDataList", repeatList);
        map.put("dataList", list);
        map.put("code", list.size() > 0 ? "0" : "1");
        return map;
    }

    /**
     * 上传excel到题库基本信息表 临时文件形式
     */
    @PostMapping(value = "/excel_to_exam_info")
    public Map<String, Object> uploadExcelToExamInfo(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 指定文件上传位置
        String uploadPath = request.getSession().getServletContext().getRealPath("/excel/upload");
        File saveFile = null;
        List<ExamInfo> list = new ArrayList<>();       //正常可插入数据
        List<ExamInfo> repeatList = new ArrayList<>();
        //数据库中重复数据
        try {
            // 判断是否为空
            if (!file.isEmpty()) {
                saveFile = new File(uploadPath + File.separator + file.getOriginalFilename());
                // 自动创建目录
                FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);
            }
            log.info("文件上传路径是:" + Objects.requireNonNull(saveFile).getAbsolutePath());
            InputStream is = new FileInputStream(saveFile);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            ExamInfo examinfo;
            Date loadDate = new Date();
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        examinfo = new ExamInfo();
                        //1.读取excel中相关数据
                        HSSFCell examtypeid = hssfRow.getCell(0);
                        HSSFCell name = hssfRow.getCell(1);
                        HSSFCell a = hssfRow.getCell(2);
                        HSSFCell b = hssfRow.getCell(3);
                        HSSFCell c = hssfRow.getCell(4);
                        HSSFCell d = hssfRow.getCell(5);
                        HSSFCell e = hssfRow.getCell(6);
                        HSSFCell correctanswer = hssfRow.getCell(7);
                        HSSFCell explaininfo = hssfRow.getCell(8);

                        //封装题库基本信息字段
                        examinfo.setExamTypeId(getValue(examtypeid)).setExamName(getValue(name)).setOptionA(getValue(a)).setOptionB(getValue(b)).setCorrectAnswer(getValue(correctanswer)).setCreateTime(loadDate);
                        if (c != null) examinfo.setOptionC(getValue(c));
                        if (d != null) examinfo.setOptionD(getValue(d));
                        if (e != null) examinfo.setOptionE(getValue(e));
                        if (explaininfo != null) examinfo.setExamExplain(getValue(explaininfo));

                        //判断数据是否在数据库中重复
                        List<ExamInfo> l = examInfoService.selectList(new EntityWrapper<ExamInfo>().eq("name", getValue(name)));
                        if (l.size() > 0) {
                            repeatList.add(examinfo);
                        } else {
                            list.add(examinfo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("repeatDataList", repeatList);
        map.put("dataList", list);
        map.put("code", list.size() > 0 ? "0" : "1");
        return map;
    }

    //Excel 数据添加
    @PostMapping(value = "/excel_add_exam_info")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String addExamInfoByExcel(String array, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<ExamInfo> list = JSON.parseArray(array, ExamInfo.class);
        try {
            for (ExamInfo examinfo : list) {
                examInfoService.insert(examinfo);
            }
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * 判断excel数据类型
     */
    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }

    }
}
