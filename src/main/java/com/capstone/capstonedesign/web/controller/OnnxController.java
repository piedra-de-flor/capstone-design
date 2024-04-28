package com.capstone.capstonedesign.web.controller;

//import com.capstone.capstonedesign.service.ONNXService;
import com.capstone.capstonedesign.service.ai.PythonRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OnnxController {
   // private final ONNXService service;
    private final PythonRunner runner;

    @GetMapping("/test")
    public void test() {
        runner.runPythonScript("C:\\Users\\USER\\Desktop\\공부\\server_connect\\database\\MOT20_02.jpg");
    }
}
