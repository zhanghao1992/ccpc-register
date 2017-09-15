package com.zhongqi.controller;

import com.zhongqi.service.MatchApplyGradeService;
import com.zhongqi.util.BaseController;
import com.zhongqi.util.ExcelUtils;
import com.zhongqi.util.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by songrenfei on 2017/7/3.
 */
@RestController
@RequestMapping("athletics")
public class AthleticsController extends BaseController {

    Logger logger = Logger.getLogger(getClass());
    @Autowired
    private MatchApplyGradeService matchApplyGradeService;

    @Value("${matchApply_Grade_path}")
    private String  matchApply_Grade_path;



//--------------------------------趣味竞技专题----------------------------------------------------------

    /**
     * 10、获取排名大师分2016列表
     */
    @ApiOperation(value = "10、获取排名大师分2016列表",notes = "10、获取排名大师分2016列表")
    @RequestMapping(value = "/personRatingRankList", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", paramType = "query",value = "page", required = true, dataType = "Integer",defaultValue = "1"),
            @ApiImplicitParam(name = "page_size", paramType = "query",value = "page_size", required = true, dataType = "Integer",defaultValue = "20"),
            @ApiImplicitParam(name = "callback", paramType = "query",value = "page_size", required = true, dataType = "String",defaultValue = "cc"),
            @ApiImplicitParam(name = "idNumber", paramType = "query",value = "page_size", required = false, dataType = "String"),
    })
    public String  personRatingRankList(HttpServletRequest request,Integer page,Integer page_size,String callback,String idNumber) throws Exception{
        ResponseResult result =new ResponseResult();
        JSONObject jsonObject =null;
        String callBacks ="";
        List<String> list =new ArrayList<>();
        if (page==null ||page ==0 ||page_size==null || page_size==0){
            result=ResponseResult.errorResult("分页页码传入错误");
            jsonObject =JSONObject.fromObject(result);
            callBacks =callback+"("+jsonObject+")";
            logger.info("分页页码传入错误："+callBacks);
            return callBacks;
        }
        Map<String,Object> map =matchApplyGradeService.personRatingRankList(page,page_size,idNumber);
        result=new ResponseResult(ResponseResult.SUCCESS,"获取列表成功",map);
        jsonObject =JSONObject.fromObject(result);
        callBacks =callback+"("+jsonObject+")";
        logger.info("大师分排名2016列表成功："+callBacks);
        return callBacks;
    }

    /**
     * 11、获取报名比赛成绩列表
     */
    @ApiOperation(value = "11、获取报名比赛成绩列表",notes = "11、获取报名比赛成绩列表")
    @RequestMapping(value = "/getMatchApplyGradeList", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", paramType = "query",value = "page", required = true, dataType = "int",defaultValue = "1"),
            @ApiImplicitParam(name = "page_size", paramType = "query",value = "page_size", required = true, dataType = "int",defaultValue = "20"),
            @ApiImplicitParam(name = "callback", paramType = "query",value = "page_size", required = true, dataType = "String",defaultValue = "cc"),
            @ApiImplicitParam(name = "idNumber", paramType = "query",value = "page_size", required = false, dataType = "String"),
            @ApiImplicitParam(name = "matchTime", paramType = "query",value = "page_size", required = false, dataType = "String",defaultValue = "2017-12-03"),
            @ApiImplicitParam(name = "type", paramType = "query",value = "page_size", required = false, dataType = "int",defaultValue="1"),
    })
    public String  getMatchApplyGradeList(HttpServletRequest request,Integer page,Integer page_size,String callback,String idNumber,
                                          String matchTime,Integer type
    ) throws Exception{
        ResponseResult result =new ResponseResult();
        JSONObject jsonObject =null;
        String callBacks ="";
        List<String> list =new ArrayList<>();
        if (type==null ){
            result=ResponseResult.errorResult("比赛类型未传入");
            jsonObject =JSONObject.fromObject(result);
            callBacks =callback+"("+jsonObject+")";
            logger.info("比赛类型未传入："+callBacks);
            return callBacks;
        }
        if (page==null ||page ==0 ||page_size==null || page_size==0){
            result=ResponseResult.errorResult("分页页码传入错误");
            jsonObject =JSONObject.fromObject(result);
            callBacks =callback+"("+jsonObject+")";
            logger.info("分页页码传入错误："+callBacks);
            return callBacks;
        }
        Map<String,Object> map =matchApplyGradeService.getMatchApplyGradeList(page,page_size,idNumber,type,matchTime);
        result=new ResponseResult(ResponseResult.SUCCESS,"获取报名比赛成绩列表成功",map);
        jsonObject =JSONObject.fromObject(result);
        callBacks =callback+"("+jsonObject+")";
        logger.info("获取报名比赛成绩列表成功："+callBacks);
        return callBacks;
    }

    //录入比赛成绩
    @RequestMapping(value = "/importMatchApplyGrade",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseResult importMatchApplyGrade(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseResult result = ResponseResult.successResult("参数校验正确");
        String path = matchApply_Grade_path;
        System.out.println(path);
        try {
            File fileNew = new File(path);
            Workbook workbook = null;
            try {
                workbook = new HSSFWorkbook(new FileInputStream(fileNew));
                System.out.println("excel format 97-2003");
            } catch (Exception e) {
                workbook = new XSSFWorkbook(new FileInputStream(fileNew));
                System.out.println("excel format 2007-2010");
            }

            ExcelUtils excelUtils = new ExcelUtils();

            List<List<List<String>>> excelList = excelUtils.resolveExcel(workbook);
            if (!excelList.isEmpty()) {
                Map<String,Object> map =matchApplyGradeService.importMatchApplyGrade(excelList);
                String list ="list";
                if (map.get(list).toString().length()<=2){
                    result =ResponseResult.successResult("导入成功的数量为"+map.get("sucessCount"));
                } else {
                    result = new ResponseResult(ResponseResult.ERROR,"导入失败的数量为"+map.get("errorCount")+";导入成功的数量为"+map.get("successCount"),
                            map.get("list"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



}