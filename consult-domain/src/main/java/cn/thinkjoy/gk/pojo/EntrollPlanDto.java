package cn.thinkjoy.gk.pojo;

import java.util.List;

/**
 * Created by wpliu on 15/9/25.
 */
public class EntrollPlanDto {

     private List<EntrollPlan> enrollPlan;
     private String entroIntro;
     private String universityIntro;

     public List<EntrollPlan> getEnrollPlan() {
          return enrollPlan;
     }

     public void setEnrollPlan(List<EntrollPlan> enrollPlan) {
          this.enrollPlan = enrollPlan;
     }

     public String getEntroIntro() {
          return entroIntro;
     }

     public void setEntroIntro(String entroIntro) {
          this.entroIntro = entroIntro;
     }

     public String getUniversityIntro() {
          return universityIntro;
     }

     public void setUniversityIntro(String universityIntro) {
          this.universityIntro = universityIntro;
     }
}
