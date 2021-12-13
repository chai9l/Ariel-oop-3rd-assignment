package api;

import com.google.gson.*;

import java.lang.reflect.Type;

public class DwGraphJsonDeserializer implements JsonDeserializer<DW_Graph> {
    @Override
    public DW_Graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        DirectedWeightedGraph g = new DW_Graph();
        JsonElement GraphNodes = jsonObject.get("Nodes");
        JsonArray nodesJson = GraphNodes.getAsJsonArray();
        for (JsonElement tmpNode : nodesJson) {
            JsonElement jasonValueElement = tmpNode.getAsJsonObject();
            int NodeKey = jasonValueElement.getAsJsonObject().get("id").getAsInt();
            String NodePos = jasonValueElement.getAsJsonObject().get("pos").getAsString();
            String[] PosArr = NodePos.split(",");
            double[] DoublePos = new double[3];
            for (int i = 0; i < PosArr.length; i++) {
                DoublePos[i] = Double.parseDouble(PosArr[i]);
            }
            NodeData n = new myNode(DoublePos[0], DoublePos[1], DoublePos[2], NodeKey);
            g.addNode(n);
        }
        JsonArray edgesJson = jsonObject.get("Edges").getAsJsonArray();
        for (JsonElement tmpEdge : edgesJson) {
            JsonElement tmpEd = tmpEdge.getAsJsonObject();
            int EdSrc = tmpEd.getAsJsonObject().get("src").getAsInt();
            int EdDest = tmpEd.getAsJsonObject().get("dest").getAsInt();
            double EdWeight = tmpEd.getAsJsonObject().get("w").getAsDouble();
            g.connect(EdSrc, EdDest, EdWeight);
        }
        return (DW_Graph) g;
    }
}
