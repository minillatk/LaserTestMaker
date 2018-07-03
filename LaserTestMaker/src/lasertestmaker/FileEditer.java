/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lasertestmaker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kimuratadashi
 */
public class FileEditer {

    public void save(String textdata) {
        String FILE_NAME;
        FILE_NAME = getTime();

        try (FileWriter FW = new FileWriter("laser" + FILE_NAME + ".gcode", true);) {
            FW.write(textdata);
        } catch (IOException ex) {
            Logger.getLogger(FileEditer.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public String getTime() {
        //java8のDateTimeクラスはnewしなくても呼び出せる
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyMMdd_HHmm_ss");//日時のフォーマット形態を設定
        LocalDateTime localdatetime = LocalDateTime.now();//.now ローカルな現在の日時を取得
        System.out.println(localdatetime);//フォーマットなし
        String getFormattingTime = localdatetime.format(formatter);//.formatで上記のフォーマット形態に変換してくれる
        System.out.println(getFormattingTime);//フォーマット後
        return getFormattingTime;
    }
}
