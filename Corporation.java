public class Corporation extends Stock {
    private double dividend;
    private double dividentPercent;
    private int dividendPayDate;
    private int dividendChangeDate;

    /**
     * Default corporation constructor
     * @param pName name of corporation, commodity, ETf, etc.
     * @param pTicker ticker of stock
     * @param pIndustry industry of the stock for classification
     * @param mean center of normal distribution for price RNG
     * @param stdDev spread of normal distribution for price RNG
     * @param pVolatility custom volatility factor
     */
    public Corporation(String pTicker, String pName, String pIndustry, double mean, double stdDev, int pVolatility) {
        super(pTicker, pName, pIndustry, mean, stdDev, pVolatility);
        dividend = super.randNormPrice(super.getPreviousClose() * 0.1, super.getPreviousClose() * 0.01);
        dividentPercent = super.round(dividend / super.getPreviousClose());
    }


    

}