package proj.savidecor.Models;

public class UpdateCART {

    private String pNAME,pID,PPRICE;
    private int position,pQUANT;

    public UpdateCART(int position, String pID, String pNAME, String PPRICE, int pQUANT) {
        this.position=position;
        this.pID = pID;
        this.pNAME = pNAME;
        this.PPRICE = PPRICE;
        this.pQUANT = pQUANT;
    }

    @Override
    public String toString() {
        return "UpdateCART{" +
                "pNAME='" + pNAME + '\'' +
                ", pID='" + pID + '\'' +
                ", PPRICE='" + PPRICE + '\'' +
                ", pQUANT=" + pQUANT +
                ", position=" + position +
                '}';
    }

    public String getpID() {
        return pID;
    }

    public String getPPRICE() {
        return PPRICE;
    }

    public int getpQUANT() {
        return pQUANT;
    }

    public String getpNAME() {
        return pNAME;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
