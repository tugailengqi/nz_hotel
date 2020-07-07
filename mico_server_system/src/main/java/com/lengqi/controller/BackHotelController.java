package com.lengqi.controller;

import com.lengqi.entity.City;
import com.lengqi.entity.Hotel;
import com.lengqi.feign.CityFeign;
import com.lengqi.feign.HotelFeign;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * 后台酒店管理
 */
@Controller
@RequestMapping("/back")
public class BackHotelController {
    @Resource
    private CityFeign cityFeign;
    @Resource
    private HotelFeign hotelFeign;
    @RequestMapping("/toAddHotel")
    public String addHotel(Model model){
        List<City> cities = cityFeign.queryCityList();
        model.addAttribute("cities",cities);
        return "addHotel";
    }
    @PostMapping("/insert")
    public String insertHotel(MultipartFile file,Hotel hotel){
        String path =  "D:\\code\\week19\\nz1903\\mico_server_system\\src\\main\\resources\\static\\resources\\image\\";
        //处理图片上传的问题
        //上传的文件名
        String name = UUID.randomUUID().toString();

        //获得当前服务器的classpath路径
        //c://xxx//xx
        String filename = file.getOriginalFilename();
//      String newFileName =  BackHotelController.class.getResource("/").getPath()+"static/image"+name;
        String newFileName =name+"."+filename.substring(filename.lastIndexOf(".")+1);
        System.out.println("上传路径：" + newFileName);

        try (
                InputStream in = file.getInputStream();
                OutputStream out = new FileOutputStream(path+newFileName)
        ) {
            IOUtils.copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将上传的图片设置到酒店对象中
        hotel.setHotalImage("http://192.168.148.1:9090/resources/image/" +newFileName);

        //调用酒店服务保存酒店信息
        hotelFeign.insertHotel(hotel);
        return "redirect:/back/hotelList";
    }
    @RequestMapping("/hotelList")
    public String hotelList(Model model){
        List<Hotel> hotels = hotelFeign.queryAll();
        model.addAttribute("hotels",hotels);
        return "hotelList";
    }
}
