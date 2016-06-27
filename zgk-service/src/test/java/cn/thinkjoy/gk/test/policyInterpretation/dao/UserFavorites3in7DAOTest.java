package cn.thinkjoy.gk.test.policyInterpretation.dao;

import cn.thinkjoy.gk.dao.IUserFavorites3in7DAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmeTest.xml")
public class UserFavorites3in7DAOTest {

//    @Autowired
//    private IAdmissionBatchDAO admissionBatchDAO;

    @Autowired
    private IUserFavorites3in7DAO userFavorites3in7DAO;

    @Test
    public void findTest(){
        System.out.println("开始调试!");
        try {
            System.out.println("开始调试insertFavorites!");
            Map<String,Object> map = new HashMap<>();
            map.put("userId",1);
            map.put("majorId",2);
            map.put("createDate",System.currentTimeMillis());
            map.put("type",1);
            boolean result = userFavorites3in7DAO.insertFavorites(map);
            if(result){
                System.out.println("insertFavorites调试正常!");
            }else {
                System.out.println("insertFavorites调试异常!");
            }
        }catch (Exception e){
            System.out.println("insertFavorites调试异常!");
            e.printStackTrace();
        }
        try {
            System.out.println("开始调试getFavoritesByMajor!");
            Map<String,Object> map = new HashMap<>();
            map.put("userId",1);
            List<Map<String,Object>> result= userFavorites3in7DAO.getFavoritesByMajor(map);
            if(result.size()>0){
                System.out.println("getFavoritesByMajor调试正常!");
            }else {
                System.out.println("getFavoritesByMajor调试异常!");
            }
        }catch (Exception e){

            System.out.println("getFavoritesByMajor调试异常!");
            e.printStackTrace();
        }
        try {
            System.out.println("开始调试insertFavorites!");
            Map<String,Object> map = new HashMap<>();
            map.put("userId",1);
            map.put("majorId",2);
            map.put("subjects","思想政治 历史");
            map.put("createDate",System.currentTimeMillis());
            map.put("type",2);
            boolean result = userFavorites3in7DAO.insertFavorites(map);
            if(result){
                System.out.println("insertFavorites调试正常!");
            }else {
                System.out.println("insertFavorites调试异常!");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("insertFavorites调试异常!");
        }
        try {
            System.out.println("开始调试getFavoritesBySubjectKey!");
            Map<String,Object> map = new HashMap<>();
            map.put("userId",1);
            List<Map<String,Object>> result= userFavorites3in7DAO.getFavoritesByMajor(map);
            if(result.size()>0){
                System.out.println("getFavoritesBySubjectKey调试正常!");
            }else {
                System.out.println("getFavoritesBySubjectKey调试异常!");
            }
        }catch (Exception e){
            System.out.println("getFavoritesBySubjectKey调试异常!");
            e.printStackTrace();
        }
        try {
            System.out.println("开始调试getFavoritesBySubject!");
            Map<String,Object> map = new HashMap<>();
            map.put("userId",1);
            map.put("subjects","思想政治 历史");
            List<Map<String,Object>> result= userFavorites3in7DAO.getFavoritesByMajor(map);
            if(result.size()>0){
                System.out.println("getFavoritesBySubject调试正常!");
            }else {
                System.out.println("getFavoritesBySubject调试异常!");
            }
        }catch (Exception e){
            System.out.println("getFavoritesBySubject调试异常!");
            e.printStackTrace();
        }
        System.out.println("调试完成!");

    }

}
