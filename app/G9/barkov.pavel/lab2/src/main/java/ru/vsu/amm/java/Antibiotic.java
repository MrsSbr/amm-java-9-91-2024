package src.main.java.ru.vsu.amm.java;

public class Antibiotic extends Medicine implements Medicinal{

    private String indication;

    public Antibiotic(String name,int volume,String indication){
        super(name,volume);
        this.indication = indication;
    }

    public  String getIndication(){
        return indication;
    }
    public void heal()
    {
        System.out.println("От бактериальных заболеваний");
    }

    public String toString(){
        return super.toString()+"\nПоказания:"+indication;
    }

    public boolean equals(Object obj) {
        Antibiotic that = (Antibiotic) obj;
        return super.equals(obj) && that.indication.equals(indication);
    }
}
