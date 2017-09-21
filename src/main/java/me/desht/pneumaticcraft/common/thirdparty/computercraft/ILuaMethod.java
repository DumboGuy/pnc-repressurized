package me.desht.pneumaticcraft.common.thirdparty.computercraft;

public interface ILuaMethod {
    public String getMethodName();

    public Object[] call(Object[] args) throws Exception;
}
