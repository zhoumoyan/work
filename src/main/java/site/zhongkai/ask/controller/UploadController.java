package site.zhongkai.ask.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import site.zhongkai.ask.entity.ExamInfo;
import site.zhongkai.ask.service.ExamInfomanageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UploadController {
    @Autowired
    private ExamInfomanageService examinfomanageService;

    /**
     * 上传excel到题库基本信息表 IO流形式
     * @param file 上传的文件名
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
    @PostMapping(value = "/uploadExcelToExamInfoByIO")
    public Map<String, Object> uploadExcelToExamInfoByIO(@RequestParam("file") MultipartFile file,
                                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");

        List<ExamInfo> list = new ArrayList<ExamInfo>();       //正常可插入数据
        List<ExamInfo> repeatList = new ArrayList<ExamInfo>();; //数据库中重复数据
        try {
            InputStream is = file.getInputStream();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            ExamInfo examinfo = null;

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
                        HSSFCell correctanswer = hssfRow.getCell(6);
                        HSSFCell explaininfo = hssfRow.getCell(7);

                        //封装题库基本信息字段
                        Double c1 = Double.parseDouble((getValue(examtypeid)));
                        int a1 = c1.intValue();
                        Integer a2 = Integer.valueOf(a1);
                        examinfo.setExamtypeid(a2);
                        examinfo.setName(getValue(name));
                        examinfo.setA(getValue(a));
                        examinfo.setB(getValue(b));
                        examinfo.setC(getValue(c));
                        examinfo.setD(getValue(d));
                        examinfo.setCorrectanswer(getValue(correctanswer));
                        examinfo.setExplaininfo(getValue(explaininfo));

                        //判断数据是否在数据库中重复
                        List<ExamInfo> l = examinfomanageService.selectList(new EntityWrapper<ExamInfo>().eq("name",getValue(name)));
                        if (l.size()>0) {
                            repeatList.add(examinfo);
                        } else {
                            list.add(examinfo);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("repeatDataList", repeatList);
        map.put("dataList", list);
        map.put("code", list.size()>0?"0":"1");
        log.info("----------->"+map);
        return map;
    }


    /**
     * 上传excel到题库基本信息表 临时文件形式
     * @param file 上传的文件名
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
    @PostMapping(value = "/uploadExcelToExamInfo")
    public Map<String, Object> uploadExcelToExamInfo(@RequestParam("file") MultipartFile file,
                         HttpServletRequest request, HttpServletResponse response) throws ParseException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 指定文件上传位置
        String uploadPath = request.getSession().getServletContext()
                .getRealPath("/excel/upload");
        String path = null;
        File saveFile = null;
        List<ExamInfo> list = new ArrayList<ExamInfo>();       //正常可插入数据
        List<ExamInfo> repeatList = new ArrayList<ExamInfo>();; //数据库中重复数据
        try {
            // 判断是否为空
            if (!file.isEmpty()) {
                saveFile = new File(uploadPath + File.separator + file.getOriginalFilename());
                // 自动创建目录
                FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);
            }
            log.info("文件上传路径是:"+saveFile.getAbsolutePath());
            InputStream is = new FileInputStream(saveFile);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            ExamInfo examinfo = null;
            //list = new ArrayList<ExamInfo>();
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
                        HSSFCell correctanswer = hssfRow.getCell(6);
                        HSSFCell explaininfo = hssfRow.getCell(7);

                        //封装题库基本信息字段
                        Double c1 = Double.parseDouble((getValue(examtypeid)));
                        int a1 = c1.intValue();
                        Integer a2 = Integer.valueOf(a1);
                        examinfo.setExamtypeid(a2);
                        examinfo.setName(getValue(name));
                        examinfo.setA(getValue(a));
                        examinfo.setB(getValue(b));
                        examinfo.setC(getValue(c));
                        examinfo.setD(getValue(d));
                        examinfo.setCorrectanswer(getValue(correctanswer));
                        examinfo.setExplaininfo(getValue(explaininfo));

                        //判断数据是否在数据库中重复
                        List<ExamInfo> l = examinfomanageService.selectList(new EntityWrapper<ExamInfo>().eq("name",getValue(name)));
                        if (l.size()>0) {
                            repeatList.add(examinfo);
                        } else {
                            list.add(examinfo);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("repeatDataList", repeatList);
        map.put("dataList", list);
        map.put("code", list.size()>0?"0":"1");
        log.info("----------->"+map);
        return map;
    }

    //Excel 数据添加
    @PostMapping(value = "/addExamInfoForExcel")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String addExamInfoForExcel(String array, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<ExamInfo> list=JSON.parseArray(array, ExamInfo.class);
        try {
            for (ExamInfo examinfo : list) {
                examinfomanageService.insert(examinfo);
            }
            return "success";
        }catch (Exception e){
            return "error";
        }
    }


    /**
     * 判断excel数据类型
     * @param hssfCell
     * @return
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
