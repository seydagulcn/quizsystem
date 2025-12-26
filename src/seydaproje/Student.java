package seydaproje;

public class Student {
    private String ogrenciNo;

    public String kimlikDogrula(String no) {
        if (no != null && no.length() == 9 && no.matches("[0-9]+")) {
            this.ogrenciNo = no;
            return "true";
        }else if(no.matches("[A-Za-z]+")) {
        	return "sadece sayi girmelisiniz.";
        }
        else {return "ogrenci no 9 karakter olmali";
        	
        }
        
    }

    public String getOgrenciNo() { return ogrenciNo; }
}