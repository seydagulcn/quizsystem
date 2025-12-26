package seydaproje;

public class Student {
    private String ogrenciNo;

    public boolean kimlikDogrula(String no) {
        if (no != null && no.length() == 9 && no.matches("[0-9]+")) {
            this.ogrenciNo = no;
            return true;
        }
        return false;
    }

    public String getOgrenciNo() { return ogrenciNo; }
}