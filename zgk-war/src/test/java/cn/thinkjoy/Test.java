package cn.thinkjoy;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * Created by admin on 2016/3/10.
 */
public class Test {
    public static void main(String[] args) {
        String string="{\n" +
                "    title: {\n" +
                "        text: '成绩走势'\n" +
                "    },\n" +
                "    tooltip : {\n" +
                "        trigger: 'axis'\n" +
                "    },\n" +
                "    legend: {\n" +
                "        data:['清华大学1','北京大学']\n" +
                "    },\n" +
                "    grid: {\n" +
                "        left: '3%',\n" +
                "        right: '4%',\n" +
                "        bottom: '3%',\n" +
                "        containLabel: true\n" +
                "    },\n" +
                "    toolbox: {\n" +
                "        feature: {\n" +
                "            saveAsImage: {}\n" +
                "        }\n" +
                "    },\n" +
                "    xAxis : [\n" +
                "        {\n" +
                "            type : 'category',\n" +
                "            boundaryGap : true,\n" +
                "            data : ['2015.3.1','2015.3.2','2015.3.3','2015.3.4','2015.3.5','2015.3.6','2015.3.7']\n" +
                "        }\n" +
                "    ],\n" +
                "    yAxis : [\n" +
                "        {\n" +
                "            type : 'value'\n" +
                "        }\n" +
                "    ],\n" +
                "    series : [\n" +
                "        {\n" +
                "            name:'清华大学1',\n" +
                "            type:'line',\n" +
                "            stack: '总量',\n" +
                "            data:[0, 332, 301, 334, 390, 330, 320]\n" +
                "        },\n" +
                "        {\n" +
                "            name:'北京大学',\n" +
                "            type:'line',\n" +
                "            stack: '总量',\n" +
                "            data:[820, 932, 901, 934, 1290, 1330, 1320]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        Map<String,Object> map= (Map<String,Object>)JSON.parse(string);
        //存放学校
        map.get("legend");

        //存放时间
        map.get("xAxis");

        //存放学校对应分数
        map.get("series");
    }
}
