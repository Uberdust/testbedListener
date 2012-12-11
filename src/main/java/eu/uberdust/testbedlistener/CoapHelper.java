package eu.uberdust.testbedlistener;

import ch.ethz.inf.vs.californium.coap.CodeRegistry;
import ch.ethz.inf.vs.californium.coap.Option;
import ch.ethz.inf.vs.californium.coap.OptionNumberRegistry;
import ch.ethz.inf.vs.californium.coap.Request;
import eu.uberdust.testbedlistener.coap.CoapServer;

/**
 * Created with IntelliJ IDEA.
 * User: amaxilatis
 * Date: 11/2/12
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class CoapHelper {

    /**
     * Generates a Well-known/core request for the selected macStr and the Selected block for blockwise-transfers.
     *
     * @param macStr the destination mac.
     * @param i      the block to request.
     * @return the CoAP request.
     */
    public static Request getWellKnown(String macStr, int i) {
        Request request = getWellKnown(macStr);
        Option blockwiseOption = new Option(OptionNumberRegistry.BLOCK2);
        byte[] block = new byte[1];
        block[0] = (byte) ((byte) i << 4);
        block[0] = (byte) (block[0] | 0x2);
        blockwiseOption.setValue(block);
        request.setOption(blockwiseOption);
        return request;
    }

    /**
     * Generates a Well-known/core request for the selected macStr.
     *
     * @param macStr the destination mac.
     * @return the CoAP request.
     */
    public static Request getWellKnown(String macStr) {
        synchronized (CoapHelper.class) {
            Request request = new Request(CodeRegistry.METHOD_GET, false);
            request.setURI("/.well-known/core");
            Option urihostOption = new Option(OptionNumberRegistry.URI_HOST);
            urihostOption.setStringValue(macStr);
            request.addOption(urihostOption);
            request.setMID(CoapServer.getInstance().nextMID());
            request.prettyPrint();
            return request;
        }
    }
}
