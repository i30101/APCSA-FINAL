public class Cryptocurrency extends Stock {


    /**
     * Default cryptocurrency constructor
     * @param pName name of corporation, commodity, ETf, etc.
     * @param pTicker ticker of stock
     * @param pIndustry industry of the stock for classification
     * @param mean center of normal distribution for price RNG
     * @param stdDev spread of normal distribution for price RNG
     * @param pVolatility custom volatility factor
     */
    public Cryptocurrency(String pTicker, String pName, String pIndustry) {
        super(pTicker, pName, pIndustry, 10000, 2000, 5);
    }
}
