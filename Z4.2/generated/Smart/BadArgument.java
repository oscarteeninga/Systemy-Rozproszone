//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.3
//
// <auto-generated>
//
// Generated from file `smarthome.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Smart;

public class BadArgument extends com.zeroc.Ice.UserException
{
    public BadArgument()
    {
    }

    public BadArgument(Throwable cause)
    {
        super(cause);
    }

    public String ice_id()
    {
        return "::Smart::BadArgument";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::Smart::BadArgument", -1, true);
        ostr_.endSlice();
    }

    /** @hidden */
    @Override
    protected void _readImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        istr_.endSlice();
    }

    /** @hidden */
    public static final long serialVersionUID = -3217310092164872950L;
}