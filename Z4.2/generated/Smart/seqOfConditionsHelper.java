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

/**
 * Helper class for marshaling/unmarshaling seqOfConditions.
 **/
public final class seqOfConditionsHelper
{
    public static void write(com.zeroc.Ice.OutputStream ostr, condition[] v)
    {
        if(v == null)
        {
            ostr.writeSize(0);
        }
        else
        {
            ostr.writeSize(v.length);
            for(int i0 = 0; i0 < v.length; i0++)
            {
                condition.ice_write(ostr, v[i0]);
            }
        }
    }

    public static condition[] read(com.zeroc.Ice.InputStream istr)
    {
        final condition[] v;
        final int len0 = istr.readAndCheckSeqSize(1);
        v = new condition[len0];
        for(int i0 = 0; i0 < len0; i0++)
        {
            v[i0] = condition.ice_read(istr);
        }
        return v;
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<condition[]> v)
    {
        if(v != null && v.isPresent())
        {
            write(ostr, tag, v.get());
        }
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, condition[] v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            seqOfConditionsHelper.write(ostr, v);
            ostr.endSize(pos);
        }
    }

    public static java.util.Optional<condition[]> read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            condition[] v;
            v = seqOfConditionsHelper.read(istr);
            return java.util.Optional.of(v);
        }
        else
        {
            return java.util.Optional.empty();
        }
    }
}
