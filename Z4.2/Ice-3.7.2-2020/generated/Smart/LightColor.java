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

public interface LightColor extends Light
{
    void setColor(color color, com.zeroc.Ice.Current current);

    color getColor(com.zeroc.Ice.Current current);

    /** @hidden */
    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::Smart::Light",
        "::Smart::LightColor",
        "::Smart::Switch"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::Smart::LightColor";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_setColor(LightColor obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        color iceP_color;
        iceP_color = color.ice_read(istr);
        inS.endReadParams();
        obj.setColor(iceP_color, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getColor(LightColor obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        color ret = obj.getColor(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        color.ice_write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "change",
        "getColor",
        "getCondition",
        "getMode",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "off",
        "on",
        "setBrightness",
        "setColor",
        "setCondition",
        "setMode"
    };

    /** @hidden */
    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return Switch._iceD_change(this, in, current);
            }
            case 1:
            {
                return _iceD_getColor(this, in, current);
            }
            case 2:
            {
                return Switch._iceD_getCondition(this, in, current);
            }
            case 3:
            {
                return Light._iceD_getMode(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 5:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 6:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 7:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 8:
            {
                return Switch._iceD_off(this, in, current);
            }
            case 9:
            {
                return Switch._iceD_on(this, in, current);
            }
            case 10:
            {
                return Light._iceD_setBrightness(this, in, current);
            }
            case 11:
            {
                return _iceD_setColor(this, in, current);
            }
            case 12:
            {
                return Switch._iceD_setCondition(this, in, current);
            }
            case 13:
            {
                return Light._iceD_setMode(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
