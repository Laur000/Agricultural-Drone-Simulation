public class Variabile {

    public float niv_p;
    public float niv_b;
    public int V_max_v;
    public boolean Act_pv;
    public boolean Act_mo;
    public float Alt;
    public int[] Vect_rpm;
    public float[] Unghi;
    public int distance;
    public int altitudine;

    public Variabile(){
        this.niv_p = 0;
        this.niv_b = 0;
        V_max_v = 0;
        Act_pv = false;
        Act_mo = false;
        Alt = 0;
        Vect_rpm = new int[7];
        Vect_rpm[0]=250;
        for(int i=1;i<7;i++)
            Vect_rpm[i]=0;
        Unghi = new float[3];
        for(int i=0;i<3;i++)
            Unghi[i]=0;
        distance=0;
        altitudine=0;
    }
}
