package com.example.test.cucumber;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

@Component
public class SharedState {
    private MvcResult result;

    public MvcResult getResult() {
        return result;
    }

    public void setResult(MvcResult result) {
        this.result = result;
    }
}
