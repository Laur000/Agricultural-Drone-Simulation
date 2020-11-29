import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Random;

public class GUI {

    private static JFrame frame;
    private static JPanel Vars;
    private static JLabel Img;
    private static int Width=1100;       //516x416
    private static int Height=450;
    private static JLabel niv_p;
    private static JLabel niv_b;
    private static JLabel V_max_v;
    private static JLabel Act_pv;
    private static JLabel Act_mo;
    private static JLabel Alt;
    private static JLabel Vect_rpm;
    private static JLabel Unghi;
    private static JLabel NivelBaterie;
    private static JLabel NivelTank;
    private static JLabel distanceText;
    private static JLabel altText;
    private static ImageIcon[] imgs;
    private static Variabile variabile;
    private static JButton StartButton;
    private static JButton PauseButton;
    private static JButton ResetButton;
    private static JButton x1;
    private static JButton x2;
    private static JButton x20;
    private static int index;
    private static boolean begin;
    private static JSlider batSlider;
    private static JSlider tankSlider;
    private static JSlider distanceSlider;
    private static JSlider altSlider;
    private static JTextArea console;
    private static JScrollPane scrollPane;
    private static int mseconds;

    private static int getRandomRange(int min, int max) {

        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();
    }

     public static void q2() {
        Random rand = new Random();
        for (int i = 1; i < variabile.Vect_rpm.length; i++) {
            int fail = rand.nextInt(101);
            if (fail < 2) {
                variabile.Vect_rpm[i] = getRandomRange(0, 249);
            } else
                variabile.Vect_rpm[i] = 250;
            if (variabile.Vect_rpm[0] > variabile.Vect_rpm[i]) {
                JOptionPane.showMessageDialog(frame, "Atentie ! Motoarele nu functioneaza in regim normal!",
                        "Alert", JOptionPane.WARNING_MESSAGE);
                index = 3;
                return;
            }
        }
        index = 4;
        console.append("Motoarele functioneaza in parametri normali!\n");
    }

    public static void q4(){
        for (int i = 1; i < variabile.Vect_rpm.length; i++) {
            variabile.Vect_rpm[i]=0;
        }
        console.append("Traseul a fost stabilit cu success\n");
        variabile.distance=distanceSlider.getValue();
        index++;
    }

    public static void q5() {
        int fail = getRandomRange(0, 100);
        if (fail < 5) {
            variabile.V_max_v =getRandomRange(31, 50);
            JOptionPane.showMessageDialog(frame, "Atentie ! Conditii meteo nefavorabile!",
                    "Alert", JOptionPane.WARNING_MESSAGE);
            index = 3;
        } else {
            console.append("Conditii meteo favorabile!\n");
            variabile.V_max_v =getRandomRange(2, 30);
            index = 6;
        }
    }

    public static void q6() {
        if (variabile.niv_b-(22+4*variabile.distance)*0.5<5) {
            JOptionPane.showMessageDialog(frame, "Atentie ! Nivel insuficient de baterie pentru ruta selectata!",
                    "Alert", JOptionPane.WARNING_MESSAGE);
            index = 3;
        } else {
            console.append("Nivel baterie suficient pentru ruta selectata!\n");
            index = 7;
        }
    }

    public static void q7() {
        if (variabile.distance*4>variabile.niv_p) {
            JOptionPane.showMessageDialog(frame, "Atentie ! Nivel insuficient de pesticid pentru ruta selectata!",
                    "Alert", JOptionPane.WARNING_MESSAGE);
            index = 3;
        } else {
            console.append("Nivel pesticid suficient pentru ruta selectata!\n");
            index = 8;
        }
    }

    public static void q8() {
        console.append("Drona este pregatita pentru pornire!\n");
        index = 9;
    }

    public static void q9() {
        for (int i = 1; i < variabile.Vect_rpm.length ; i++) {
            variabile.Vect_rpm[i] = 1000;
        }
        index = 10;
    }

    public static void q10() {
        console.append("Decolare...\n");
        for (int i = 1; i < variabile.Vect_rpm.length ; i++) {
            variabile.Vect_rpm[i] = 5000;
        }
        while(variabile.Alt < variabile.altitudine) {
            update();
            variabile.Alt+=0.2f;
            try{
                Thread.sleep(80);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        for (int i = 1; i < variabile.Vect_rpm.length ; i++) {
            variabile.Vect_rpm[i] = 2500;
        }
        index = 11;
    }

    public static void q11() {
        console.append("Drona se afla la coordonatele \nde inceput ale traseului...\n");
        index = 12;
    }

    public static void q12() {
        variabile.Unghi[0]=30;
        variabile.Act_pv=false;
        int obstChance = getRandomRange(0, 100);
        if (obstChance < 12 && obstChance >6) {
            console.append("Obstacol detectat!\n");
            index = 14;
            return;
        }

        if (obstChance < 6 && obstChance >2) {
            //drone destabilizata
            index = 15;
            return;
        }

        if (obstChance < 1 && obstChance >0) {
            //motor defect
            for(int i=0;i<3;i++){
                variabile.Unghi[i]=getRandomRange(-60,60);
            }
            index = 16;
            return;
        }

        index = 13;
        return;

    }

    public static void q14() {
        int fail = getRandomRange(0 ,100);
        if (fail < 2) {
            index = 17;
            for (int i = 1; i < variabile.Vect_rpm.length ; i++) {
                variabile.Vect_rpm[i] = 5000;
            }
            while(variabile.Alt > 0) {
                update();
                variabile.Alt-=0.2;
                try{
                    Thread.sleep(10);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            variabile.Alt=0;
            return;
        }
        while(variabile.Alt < variabile.altitudine+2) {
            update();
            variabile.Alt+=0.2;
            try{
                Thread.sleep(80);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        while(variabile.Alt > variabile.altitudine) {
            update();
            variabile.Alt-=0.2;
            try{
                Thread.sleep(80);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        console.append("Obstacolul a fost evitat cu succes!\n");
        index = 12;
        return;
    }

    public static void q15() {
        int fail = getRandomRange(0 ,100);
        if (fail < 2) {
            index = 17;
            for(int i=0;i<3;i++){
                variabile.Unghi[i]=getRandomRange(-60,60);
            }
            while(variabile.Alt > 0) {
                update();
                variabile.Alt-=0.2;
                try{
                    Thread.sleep(10);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            return;
        }
        console.append("Drona s-a stabilizat cu succes!\n");
        index = 12;
        return;
    }

    public static void q16() {
        int chance = getRandomRange(1,3);
        for(int i=1;i<variabile.Vect_rpm.length;i++){
            if(i<chance)
                variabile.Vect_rpm[i]=0;
            else
                variabile.Vect_rpm[i]=4000;
        }
        console.append("Defectiune la motoare!\n");
        for(int i=0;i<3;i++){
            variabile.Unghi[i]=0;
        }
        index = 18;
    }

    public static void q17() {
        for (int i = 1; i < variabile.Vect_rpm.length ; i++) {
            variabile.Vect_rpm[i] = 0;
        }
        JOptionPane.showMessageDialog(frame, "Impact detectat! Drona se opreste!\n",
                "Alert", JOptionPane.WARNING_MESSAGE);
        variabile.Alt = 0;
        begin=false;
    }

    public static void q18() {
        console.append("Aterizare de urgenta !\n");
        while(variabile.Alt > 0) {
            update();
            variabile.Alt-=0.2;
            try{
                Thread.sleep(80);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        variabile.Alt=0;
        begin=false;
    }

    public static void q13() {
        console.append("Parcurgere traseu...\nZone marcate ramase: "+variabile.distance+"\n");
        q12();
        if(index==13){
            for(int i=0;i<3;i++){
                variabile.Unghi[i]=0;
            }
            variabile.Act_pv=true;
            index = 19;
        }
        //afisam cv cum a pulverizat o zona
    }

    public static void q19() {
        variabile.distance--;
        variabile.Act_pv=false;
        if (variabile.distance > 0) {
            index = 12;
        } else {
            index = 20;
            //afisam cv cum ca s-au terminat zonele marcate
        }
    }

    public static void q20() {
        //afisam cum ca traseul a fost finalizat
        console.append("Traseul a fost finalizat cu succes!\n");
        index = 21;
        variabile.Unghi[0]=30;
    }

    public static void q21() {
        //drona s-a intors la coord initiale
        console.append("Drona s-a intors la coordonatele initiale!\n");
        index = 22;
    }

    public static void q22() {
        //drona aterizeaza
        for (int i = 1; i < variabile.Vect_rpm.length ; i++) {
            variabile.Vect_rpm[i] = 2000;
        }
        console.append("Aterizare!\n");
        while(variabile.Alt > 0) {
            update();
            variabile.Alt-=0.2;
            try{
                Thread.sleep(80);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        for (int i = 1; i < variabile.Vect_rpm.length ; i++) {
            variabile.Vect_rpm[i] = 0;
        }
        variabile.Alt=0;
        begin=false;
    }

    public static void main(String[] args) {
        variabile = new Variabile();
        begin=false;
        initialize();
        index=0;
        Listeners();

        while(true) {
            if(begin) {
                switch (index){
                    case(0): {index++; updateImg(index);} break;
                    case(1): {index++; updateImg(index);} break;
                    case(2): {q2(); Img.setIcon(imgs[index]);} break;
                    case(3): {updateImg(index); begin=false;} break;
                    case(4): {q4(); updateImg(index);} break;
                    case(5): {q5(); updateImg(index);} break;
                    case(6): {q6(); updateImg(index);} break;
                    case(7): {q7(); updateImg(index);} break;
                    case(8): {q8(); updateImg(index);} break;
                    case(9): {q9(); updateImg(index);} break;
                    case(10): {q10(); updateImg(index);} break;
                    case(11): {q11(); updateImg(index);} break;
                    case(12): {q12(); updateImg(index);} break;
                    case(13): {q13(); updateImg(index);} break;
                    case(14): {q14(); updateImg(index);} break;
                    case(15): {q15(); updateImg(index);} break;
                    case(16): {q16(); updateImg(index);} break;
                    case(17): {q17(); updateImg(index);} break;
                    case(18): {q18(); updateImg(index);} break;
                    case(19): {q19(); updateImg(index);} break;
                    case(20): {q20(); updateImg(index);} break;
                    case(21): {q21(); updateImg(index);} break;
                    case(22): {q22(); updateImg(index);} break;
                }
                update();
                if(variabile.niv_b>1)
                    variabile.niv_b-=0.5;
                if(variabile.Act_pv)
                    if(variabile.niv_p>0)
                        variabile.niv_p-=4.0f;
        }
            frame.repaint();
            frame.revalidate();
            try{
                Thread.sleep(mseconds);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }

    }

    public static void update(){
        niv_p.setText("Nivel pesticid: "+String.format("%.1f", variabile.niv_p)+"%");
        niv_b.setText("Nivel baterie: "+String.format("%.1f", variabile.niv_b)+"%");
        V_max_v.setText("Viteza vant: "+variabile.V_max_v+"Km/h  Max:30Km/h");
        Act_pv.setText("Activare pulverizare: "+variabile.Act_pv);
        Act_mo.setText("Activare motoare: "+variabile.Act_mo);
        Alt.setText("Altitudine: "+String.format("%.1f", variabile.Alt)+"m");
        Vect_rpm.setText("RPM Motoare: M1: "+variabile.Vect_rpm[1]+"     M2: "+variabile.Vect_rpm[2]+
                "     M3: "+variabile.Vect_rpm[3]+"     M4: "+variabile.Vect_rpm[4]+"     M5: "+variabile.Vect_rpm[5]+"     M6: "+variabile.Vect_rpm[6]);
        Unghi.setText("Unghi: X: "+variabile.Unghi[0]+"     Y: "+variabile.Unghi[1]+"     Z: "+variabile.Unghi[2]);
    }

    public static void updateImg(int index){
        Img.setIcon(imgs[index]);
    }

    public static void Listeners(){
        StartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!begin){
                    begin=true;
                    PauseButton.setVisible(true);
                    StartButton.setVisible(false);
                }
                variabile.altitudine=altSlider.getValue();
                variabile.niv_b=batSlider.getValue();
                variabile.niv_p=tankSlider.getValue();
            }
        });

        PauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(begin){
                    begin=false;
                    PauseButton.setText("<html>RESUME<br>SIMULATION</html>");
                }
                else{
                    begin=true;
                    PauseButton.setText("<html>PAUSE<br>SIMULATION</html>");
                }
            }
        });

        ResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartButton.setVisible(true);
                PauseButton.setVisible(false);
                console.setText("");
                index = 0;
                updateImg(index);
                variabile = new Variabile();
                begin = false;
            }
        });

        x1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mseconds=2000;
            }
        });

        x2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mseconds=1000;
            }
        });

        x20.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mseconds=100;
            }
        });
    }

    public static void initialize(){
        mseconds=2000;
        frame= new JFrame("Drona Agricola");
        frame.setBounds(400,300,Width,Height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        TitledBorder title2 = BorderFactory.createTitledBorder("Consola");
        title2.setTitleJustification(TitledBorder.LEFT);



        console = new JTextArea();
        scrollPane = new JScrollPane(console);
        scrollPane.setBorder(title2);
        console.setEditable(false);
        DefaultCaret caret = (DefaultCaret)console.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scrollPane.setBounds(220,30,320,150);

        NivelBaterie = new JLabel("Nivel Baterie");
        NivelBaterie.setBounds(200,340,100,10);
        batSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
        batSlider.setBounds(150,350,200,50);
        batSlider.setPaintLabels(true);
        batSlider.setMajorTickSpacing(25);
        batSlider.setMinorTickSpacing(5);
        batSlider.setPaintTicks(true);
        Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
        table.put (0, new JLabel("0%"));
        table.put (25, new JLabel("25%"));
        table.put (50, new JLabel("50%"));
        table.put (75, new JLabel("75%"));
        table.put (100, new JLabel("100%"));
        batSlider.setLabelTable(table);
        NivelTank = new JLabel("Nivel Pesticid");
        NivelTank.setBounds(200,270,100,10);
        tankSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
        tankSlider.setPaintLabels(true);
        tankSlider.setLabelTable(table);
        tankSlider.setMajorTickSpacing(25);
        tankSlider.setMinorTickSpacing(5);
        tankSlider.setPaintTicks(true);
        tankSlider.setBounds(150,280,200,50);

        distanceSlider= new JSlider(JSlider.VERTICAL,0,25,0);
        distanceSlider.setBounds(380,280,50,120);
        distanceSlider.setPaintLabels(true);
        distanceSlider.setMajorTickSpacing(5);
        distanceSlider.setMinorTickSpacing(1);
        distanceSlider.setPaintTicks(true);
        distanceText= new JLabel("Distanta traseu");
        distanceText.setBounds(350,270,100,10);

        altSlider= new JSlider(JSlider.VERTICAL,0,25,0);
        altSlider.setBounds(480,280,50,120);
        altSlider.setPaintLabels(true);
        altSlider.setMajorTickSpacing(5);
        altSlider.setMinorTickSpacing(1);
        altSlider.setPaintTicks(true);
        altText= new JLabel("Altitudine");
        altText.setBounds(465,270,100,10);

        ResetButton = new JButton("RESET");
        ResetButton.setBounds(20,280,100,50);

        StartButton = new JButton("START");
        StartButton.setBounds(20,350,100,50);
        PauseButton = new JButton("<html>PAUSE<br>SIMULATION</html>");
        PauseButton.setBounds(20,350,100,50);
        PauseButton.setVisible(false);

        x1 = new JButton("x1");
        x1.setBounds(290,180,60,30);
        x2 = new JButton("x2");
        x2.setBounds(350,180,60,30);
        x20 = new JButton("x20");
        x20.setBounds(410,180,60,30);



        niv_p = new JLabel("Nivel pesticid: "+variabile.niv_p+"%");
        niv_p.setBounds(20,30,150,20);
        niv_b = new JLabel("Nivel baterie: "+variabile.niv_b+"%");
        niv_b.setBounds(20,60,150,20);
        V_max_v = new JLabel("Viteza vant: "+variabile.V_max_v+"Km/h  Max:30Km/h");
        V_max_v.setBounds(20,90,200,20);
        Act_pv = new JLabel("Activare pulverizare: "+variabile.Act_pv);
        Act_pv.setBounds(20,120,150,20);
        Act_mo = new JLabel("Activare motoare: "+variabile.Act_mo);
        Act_mo.setBounds(20,150,150,20);
        Alt = new JLabel("Altitudine: "+variabile.Alt+"m");
        Alt.setBounds(20,180,150,20);
        Vect_rpm = new JLabel("RPM Motoare: Mtest: "+variabile.Vect_rpm[0]+"     M1: "+variabile.Vect_rpm[1]+"     M2: "+variabile.Vect_rpm[2]+
                "     M3: "+variabile.Vect_rpm[3]+"     M4: "+variabile.Vect_rpm[4]+"     M5: "+variabile.Vect_rpm[5]+"     M6: "+variabile.Vect_rpm[6]);
        Vect_rpm.setBounds(20,210,500,20);
        Unghi = new JLabel("Unghi: X: "+variabile.Unghi[0]+"     Y: "+variabile.Unghi[1]+"     Z: "+variabile.Unghi[2]);
        Unghi.setBounds(20,240,300,20);




        Img=new JLabel();
        Img.setBounds(0,-20,Width,Height);
        imgs = new ImageIcon[23];
        imgs[0] = new ImageIcon("resources/img0.png");
        for(int i=1;i<=22;i++){
            imgs[i] = new ImageIcon("resource/img"+i+".png");
        }

        Img.setIcon(imgs[0]);

        Vars=new JPanel();
        Vars.setLayout(null);
        Vars.setBounds(Width/2-30,0,Width/2+10,Height-40);
        Vars.add(niv_p);
        Vars.add(niv_b);
        Vars.add(V_max_v);
        Vars.add(Act_pv);
        Vars.add(Act_mo);
        Vars.add(Alt);
        Vars.add(Vect_rpm);
        Vars.add(Unghi);
        Vars.add(StartButton);
        Vars.add(batSlider);
        Vars.add(tankSlider);
        Vars.add(NivelBaterie);
        Vars.add(NivelTank);
        Vars.add(distanceSlider);
        Vars.add(distanceText);
        Vars.add(ResetButton);
        Vars.add(scrollPane);
        Vars.add(PauseButton);
        Vars.add(altSlider);
        Vars.add(altText);
        Vars.add(x1);
        Vars.add(x2);
        Vars.add(x20);

        TitledBorder title = BorderFactory.createTitledBorder("Variabile");
        title.setTitleJustification(TitledBorder.LEFT);
        Vars.setBorder(title);

        frame.add(Vars);
        frame.add(Img);
        frame.setVisible(true);
    }
}