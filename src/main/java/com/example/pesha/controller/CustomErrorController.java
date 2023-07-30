package com.example.pesha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @Autowired
    public CustomErrorController(ErrorAttributes errorAttributes) {
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // 獲取原始的 HTTP 錯誤狀態碼
        int statusCode = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);


        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            return "error";
        }

        // 其他錯誤狀態碼可以按需處理，例如返回其他自定義的錯誤頁面
        return "error";
    }

}
