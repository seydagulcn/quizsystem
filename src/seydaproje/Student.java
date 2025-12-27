package seydaproje;

public class Student {
    private String ogrenciNo;
    public String kimlikDogrula(String no) {
    	

    	   
    	    String[] gecerliOgrenciler = {
    	        "202012345",
    	        "202045678",
    	        "202078901",
    	        "202011223",
    	        "202099887"
    	    };


    	    if (no.matches("[A-Za-z]+")) {
    	        return "sadece sayi girmelisiniz.";
    	    }

    	 
    	    if (!no.matches("[0-9]+") || no.length() != 9) {
    	        return "ogrenci no 9 karakter olmali";
    	    }

    	    for (String ogrNo : gecerliOgrenciler) {
    	        if (ogrNo.equals(no)) {
    	            this.ogrenciNo = no;
    	            return "true";
    	        }
    	    }

    	    return "ogrenci numarasi sistemde kayitli degil";
    	}
        

    public String getOgrenciNo() { return ogrenciNo; }
}