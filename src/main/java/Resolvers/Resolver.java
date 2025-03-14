package Resolvers;

import java.io.File;
import java.nio.file.Path;

public class Resolver {

    static {
        String currentDirectory = new File("./").getAbsolutePath();
        String path = Path.of(currentDirectory
                .substring(0,currentDirectory.length()-2),
                "src/main/java/Resolvers/EVT_Query.dll").toString();
        System.load(path);
    }

    public native int Add(int a, int b);


    public native String Query(String queryPath);


    public native Object QueryObject();


    public native void QueryChannelsNext(String queryPath,int fromEventRecordId);
    public native String QueryChannelsPrev(String queryPath,int fromEventRecordId);

    public native int GetTotalLogsFromChannel(String queryPath);


    public native String GetEvents(String queryPath);


    public native void FetchAllData(String queryPath);


}
