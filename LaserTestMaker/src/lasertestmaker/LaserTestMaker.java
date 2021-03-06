/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lasertestmaker;

/**
 *
 * @author minillatk
 */
public class LaserTestMaker {
    public final String GCODE_LASER_ON = "M3";//"M106"; //レーザーONのG-code命令
    public final String GCODE_LASER_OFF = "M3"; //レーザーOFFのG-code命令
    public int laser_power; //レーザーの出力0-255 標準出力8W=136 (＊最高出力15W=255 使用不可)
    public double X_default, Y_default, Z_default;  //デフォルト値
    public double X_MV, Y_MV, Z_MV, F_MV; //移動量 例＞10 = 10mm間隔で移動
    public double Laser_line_width;//レーザーで焼き付ける線の長さ
    
    
    LaserTestMaker(){ //コンストラクタ
        this.laser_power = 255;
        this.X_default = 0;
        this.Y_default = 0;
        this.Z_default = 0;
        this.X_MV = 0;
        this.Y_MV = 5;
        this.Z_MV = 0.25;
        this.F_MV = 500;
        this.Laser_line_width = 30;
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LaserTestMaker LTM = new LaserTestMaker();//staticであるmainからstaticでないメソッドなどを使う場合はnewする。
        System.out.println(LTM.headerScript());
        System.out.println(LTM.mainScript());
        System.out.println(LTM.footerScript());
    }
    
    String mainScript(){
        double x = 0, y = 0 ,z = 0;
        
        StringBuilder sb = new StringBuilder();
        sb.append("          ;メインスタート\n ");
        sb.append("G0 F" + F_MV + "\n ");
        sb.append("G0 " + "X" + this.X_default + " Y" + this.Y_default + "\n ");
        for(int i=0 ;i<10;i++){
            sb.append(GCODE_LASER_ON + " S" + this.laser_power + "      ;レーザーON\n ");
            sb.append("G0 " + "X" + x + " Y" + y + ";in\n ");
            x += Laser_line_width;
            sb.append("G0 " + "X" + x + " Y" + y + ";out\n ");
            x -= Laser_line_width;
            sb.append(GCODE_LASER_OFF + " S1        ;レーザーOFF\n ");
            y += this.Y_MV;
            z += this.Z_MV;
            sb.append("G0 " + "X" + x + " Y" + y + " Z" + z + ";Y移動\n ");
        }
        sb.append("\n          ;メインここまで");
        return sb.toString();
    }
    
    String headerScript(){
        String header = 
                GCODE_LASER_OFF + " S1   ;M107ファン(レーザー)の電源を切るコマンド S〜 出力0〜255\n" +
                "\n" +
                "G90       ;座標を絶対値指定へ変更\n" +
                "G21       ;単位を㎜に設定するコマンド\n";
    return header;
    }
    String footerScript(){
        String footer = 
                GCODE_LASER_OFF + " S1\n" +
                "G0 F3000\n" +
                "G0 X0 Y0 Z0\n" +
                "M18        ;すべてのステッパーモーターの電源をオフまたは回転をオフ（すべてのステッパーモーターを無効にする）";
    return footer;
    }

}
