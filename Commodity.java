public class Commodity extends Stock{


    /**
     * Default commodity constructor
     * @param pName name of corporation, commodity, ETf, etc.
     * @param pTicker ticker of stock
     * @param pIndustry industry of the stock for classification
     * @param mean center of normal distribution for price RNG
     * @param stdDev spread of normal distribution for price RNG
     * @param pVolatility custom volatility factor
     */
    public Commodity(String pTicker, String pName, String pIndustry, double mean, double stdDev, int pVolatility) {
        super(pTicker, pName, pIndustry, 200, 100, 0.5);
        
    }
}
