package me.desht.pneumaticredux.common.config;

import java.io.File;
import java.io.IOException;

public interface ISubConfig {
    public String getFolderName();

    public void init(File file) throws IOException;

    public void postInit() throws IOException;
}
