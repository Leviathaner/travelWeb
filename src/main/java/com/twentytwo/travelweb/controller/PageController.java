package com.twentytwo.travelweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twentytwo.travelweb.entity.News;
import com.twentytwo.travelweb.entity.NewsInfo;
import com.twentytwo.travelweb.entity.PageBean;
import com.twentytwo.travelweb.entity.Product;
import com.twentytwo.travelweb.service.NewsService;
import com.twentytwo.travelweb.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PageController {
    @Autowired
    ProductService productService;

    @Autowired
    NewsService newsService;

    @GetMapping("")
    public String showIndexFirst(HttpServletRequest hsp){
        hsp.getSession().invalidate();
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String showIndex(){
        return "foreground/index";
    }


    @GetMapping("/route_list")
    public String showRouteList(){
        return "foreground/route_list";
    }

    @GetMapping("/news_list")
    public String showNewsList(){
        return "foreground/news_list";
    }

    @GetMapping("getServerName")
    public void getServerName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //http://localhost:8080/travel_war_exploded
        String serverName = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        myWriteValue(response, serverName);
    }
    private void myWriteValue(HttpServletResponse response, Object object) throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),object);

    }

    @GetMapping("pageQuery")
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {


        //接收参数
        String currentPageStr=request.getParameter("currentPage");
        String pageSizeStr=request.getParameter("pageSize");
        String cidStr=request.getParameter("category_id");
        String category_name=request.getParameter("category_name");



        int category_id=0;
        if (StringUtils.isNotBlank(cidStr)){
            category_id=Integer.parseInt(cidStr);
        }

        int pageSize=0;
        if (StringUtils.isNotBlank(pageSizeStr)){
            pageSize=Integer.parseInt(pageSizeStr);
        }else {
            pageSize=5;
        }

        int currentPage=0;
        if (StringUtils.isNotBlank(currentPageStr)){
            currentPage=Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;
        }

       PageBean routePageBean = null;

        if(category_id==-1){
            category_id = 0;
        }

        if(3!=category_id){

            routePageBean = productService.pageQuery(category_id, currentPage, pageSize,category_name);
        }else{
            routePageBean = newsService.pageQuery(category_id, currentPage, pageSize,category_name);

        }



        myWriteValue(response,routePageBean);
    }


    @GetMapping("clickEightRank")
    public void clickFourRank(HttpServletResponse response) throws IOException {
        List<Product> fourRouteList = productService.clickEightRank();
        myWriteValue(response,fourRouteList);
    }
    @GetMapping("newsList")
    public void newsList(HttpServletResponse response) throws IOException {
        List<NewsInfo> newsList = productService.newsList();
        myWriteValue(response,newsList);
    }

    @GetMapping("theNewFour")
    public void theNewFour(HttpServletResponse response) throws IOException {
        List<Product> fourRouteList = productService.theNewFour();
        myWriteValue(response,fourRouteList);
    }

    @GetMapping("randRoute")
    public void randRoute(HttpServletResponse response) throws IOException {

        List<Product> randRoute = productService.findRandEightRoute();
        myWriteValue(response,randRoute);
    }
    @GetMapping("/header")
    public String showHeader(){
        return "foreground/header";
    }

    @GetMapping("/footer")
    public String showFooter(){
        return "foreground/footer";
    }
}
