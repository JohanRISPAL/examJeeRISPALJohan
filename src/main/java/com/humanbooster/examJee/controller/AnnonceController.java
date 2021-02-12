package com.humanbooster.examJee.controller;

import com.humanbooster.examJee.exporter.ExporterPdf;
import com.humanbooster.examJee.model.Annonce;
import com.humanbooster.examJee.service.AnnonceService;
import com.humanbooster.examJee.utils.FileUploadUtil;
import com.lowagie.text.DocumentException;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "")
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView listAnnonce(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("annonces-list");

        List<Annonce> annonces = this.annonceService.getAll();
        mv.addObject("annonces", annonces);

        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createAnnonce(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("form");

        Annonce annonce = new Annonce();
        mv.addObject("annonce", annonce);

        return mv;
    }

    @RequestMapping(value = "/detail/{annonce}", method = RequestMethod.GET)
    public ModelAndView detailAnnonce(@PathVariable(name = "annonce") Annonce annonce){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("annonce-detail");

        mv.addObject("annonce", annonce);

        return mv;
    }

    @RequestMapping(value = "/edit/{annonce}", method = RequestMethod.GET)
    public ModelAndView editAnnonce(@PathVariable(name = "annonce") Annonce annonce){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("form");

        mv.addObject("annonce", annonce);
        return mv;
    }
    @RequestMapping(value = "/edit/{annonce}", method = RequestMethod.POST)
    public ModelAndView editAnnonce(@RequestParam("image") MultipartFile multipartFile, @Valid Annonce annonce, BindingResult bindingResult) throws IOException {

        if(bindingResult.hasErrors()){
            return new ModelAndView("/form");
        }else{

            if(!multipartFile.isEmpty()){
                String uniqueID = UUID.randomUUID().toString();

                annonce.setUrl(uniqueID+".jpg");

                Annonce savedAnnonce = this.annonceService.save(annonce);

                String uploadDir = new FileSystemResource("src/main/resources/static/img").getFile().getAbsolutePath();

                FileUploadUtil.saveFile(uploadDir, uniqueID+".jpg", multipartFile);
            }

            this.annonceService.save(annonce);
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/delete/{annonce}", method = RequestMethod.GET)
    public ModelAndView deleteCandidate(@PathVariable(name = "annonce") Annonce annonce){
        this.annonceService.deleteAnnonce(annonce);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView addAnnonce(@RequestParam("image") MultipartFile multipartFile, @Valid Annonce annonce, BindingResult bindingResult) throws IOException {

        String uniqueID = UUID.randomUUID().toString();

        annonce.setUrl(uniqueID+".jpg");

        LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);;
        annonce.setPublicationDate(date);

        Annonce savedAnnonce = this.annonceService.save(annonce);

        String uploadDir = new FileSystemResource("src/main/resources/static/img").getFile().getAbsolutePath();

        FileUploadUtil.saveFile(uploadDir, uniqueID+".jpg", multipartFile);

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/exportPdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Liste_des_annonces.pdf";
        response.setHeader(headerKey, headerValue);

        List<Annonce> annonces = this.annonceService.getAll();

        ExporterPdf exporter = new ExporterPdf(annonces);
        exporter.export(response);

    }
}
