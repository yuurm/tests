package tests.app;

import lombok.Data;

import java.util.List;

@Data
public class Test {
    private int id;
    private String title;
    private String value;
    private List<Test> values;
}
