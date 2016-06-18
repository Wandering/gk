package cn.thinkjoy.gk.test.policyInterpretation.dao;

import cn.thinkjoy.gk.dao.IUserFavorites3in7DAO;
import cn.thinkjoy.gk.dao.IZGK3in7DAO;
import cn.thinkjoy.push.domain.sms.SMSCheckCode;
import cn.thinkjoy.push.service.sms.SMSService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:springmeTest.xml")
public class ZGK3in7DaoTest {

//    @Autowired
//    private IAdmissionBatchDAO admissionBatchDAO;

    @Autowired
    private IZGK3in7DAO zgk3in7DAO;

    @Test
    public void findTest(){
        System.out.println("开始调试!");
        try {
            System.out.println("开始调试getUnversityByArea!");
            Map<String,Object> map = new HashMap<>();
            map.put("areaId","110000");
            map.put("unversityName","北京");
            List<Map<String,Object>> result= zgk3in7DAO.getUnversityByArea(map);
            if(result.size()>0){
                System.out.println("getUnversityByArea调试正常!");
            }else {
                System.out.println("getUnversityByArea调试异常!");
            }
        }catch (Exception e){
            System.out.println("getUnversityByArea调试异常!");
            e.printStackTrace();
        }
        try {
            System.out.println("开始调试getMajorByUnversityId!");
            Map<String,Object> map = new HashMap<>();
            map.put("universityId","10001");
            map.put("majorName","国际政治");
            List<Map<String,Object>> result= zgk3in7DAO.getMajorByUnversityId(map);
            if(result.size()>0){
                System.out.println("getMajorByUnversityId调试正常!");
            }else {
                System.out.println("getMajorByUnversityId调试异常!");
            }
        }catch (Exception e){
            System.out.println("getMajorByUnversityId调试异常!");
            e.printStackTrace();
        }

        try {
            System.out.println("开始调试getSubjectByMajor!");
            Map<String,Object> map = new HashMap<>();
            map.put("userId","1");
            map.put("majorId","2");
            Map<String,Object> result= zgk3in7DAO.getSubjectByMajor(map);
            if(result!=null){
                System.out.println("getSubjectByMajor调试正常!");
            }else {
                System.out.println("getSubjectByMajor调试异常!");
            }
            map = new HashMap<>();
            map.put("majorId","2");
            result= zgk3in7DAO.getSubjectByMajor(map);
            if(result!=null){
                System.out.println("getSubjectByMajor调试正常!");
            }else {
                System.out.println("getSubjectByMajor调试异常!");
            }
        }catch (Exception e){
            System.out.println("getSubjectByMajor调试异常!");
            e.printStackTrace();
        }

        try {
            System.out.println("开始调试queryPage!");
            Map<String,Object> map = new HashMap<>();
            Map<String,Object> submap=null;
            List<Map<String,Object>> list=new ArrayList<>();
            submap=new HashMap<>();
            submap.put("selectSubject","地理");
            list.add(submap);
            submap=new HashMap<>();
            submap.put("selectSubject","历史");
            list.add(submap);
            map.put("subjectItem",list);
            map.put("unversityName","北京");
            map.put("userId","1");
            List<Map<String,Object>> result= zgk3in7DAO.queryPage(map);
            if(result.size()>0){
                System.out.println("queryPage调试正常!");
            }else {
                System.out.println("queryPage调试异常!");
            }
        }catch (Exception e){
            System.out.println("queryPage调试异常!");
            e.printStackTrace();
        }
        System.out.println("调试完成!");

    }

}
