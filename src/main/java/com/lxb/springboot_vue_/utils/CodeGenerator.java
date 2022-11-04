package com.lxb.springboot_vue_.utils;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * @author XBlib
 * @version 1.0
 */
public class CodeGenerator {
    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/vue_db?serverTimezone=GMT%2b8","lxb","lxb")
                .globalConfig(builder -> {
                    builder.author("XBlib")
                            .fileOverride()
                            .outputDir("D:\\JavaProject\\boot_vue\\src\\main\\java\\");
                })
                .packageConfig(builder -> {
                    builder.parent("com.lxb.springboot_vue_")
                            .moduleName("")
                            .pathInfo(Collections.singletonMap(OutputFile.xml,"D:\\JavaProject\\boot_vue\\src\\main\\resources\\com\\lxb\\springboot_vue_\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_simple")
                            .addTablePrefix("t_","c_");
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_simple")
                            .addTablePrefix("t_","c_");
                })
                .execute();
    }
}
