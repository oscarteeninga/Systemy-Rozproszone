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

public interface FrigdePrx extends SwitchPrx
{
    default void setTemp(float degrees, unit unit)
        throws UnreachableArgument
    {
        setTemp(degrees, unit, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void setTemp(float degrees, unit unit, java.util.Map<String, String> context)
        throws UnreachableArgument
    {
        try
        {
            _iceI_setTempAsync(degrees, unit, context, true).waitForResponseOrUserEx();
        }
        catch(UnreachableArgument ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Void> setTempAsync(float degrees, unit unit)
    {
        return _iceI_setTempAsync(degrees, unit, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> setTempAsync(float degrees, unit unit, java.util.Map<String, String> context)
    {
        return _iceI_setTempAsync(degrees, unit, context, false);
    }

    /**
     * @hidden
     * @param iceP_degrees -
     * @param iceP_unit -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_setTempAsync(float iceP_degrees, unit iceP_unit, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "setTemp", null, sync, _iceE_setTemp);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeFloat(iceP_degrees);
                     unit.ice_write(ostr, iceP_unit);
                 }, null);
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_setTemp =
    {
        UnreachableArgument.class
    };

    default float getTemp(unit unit)
    {
        return getTemp(unit, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default float getTemp(unit unit, java.util.Map<String, String> context)
    {
        return _iceI_getTempAsync(unit, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.Float> getTempAsync(unit unit)
    {
        return _iceI_getTempAsync(unit, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Float> getTempAsync(unit unit, java.util.Map<String, String> context)
    {
        return _iceI_getTempAsync(unit, context, false);
    }

    /**
     * @hidden
     * @param iceP_unit -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Float> _iceI_getTempAsync(unit iceP_unit, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Float> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getTemp", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     unit.ice_write(ostr, iceP_unit);
                 }, istr -> {
                     float ret;
                     ret = istr.readFloat();
                     return ret;
                 });
        return f;
    }

    default void setHumidity(int humidity)
        throws UnreachableArgument
    {
        setHumidity(humidity, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void setHumidity(int humidity, java.util.Map<String, String> context)
        throws UnreachableArgument
    {
        try
        {
            _iceI_setHumidityAsync(humidity, context, true).waitForResponseOrUserEx();
        }
        catch(UnreachableArgument ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Void> setHumidityAsync(int humidity)
    {
        return _iceI_setHumidityAsync(humidity, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> setHumidityAsync(int humidity, java.util.Map<String, String> context)
    {
        return _iceI_setHumidityAsync(humidity, context, false);
    }

    /**
     * @hidden
     * @param iceP_humidity -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_setHumidityAsync(int iceP_humidity, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "setHumidity", null, sync, _iceE_setHumidity);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeInt(iceP_humidity);
                 }, null);
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_setHumidity =
    {
        UnreachableArgument.class
    };

    default int getHumidity()
    {
        return getHumidity(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default int getHumidity(java.util.Map<String, String> context)
    {
        return _iceI_getHumidityAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.Integer> getHumidityAsync()
    {
        return _iceI_getHumidityAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Integer> getHumidityAsync(java.util.Map<String, String> context)
    {
        return _iceI_getHumidityAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Integer> _iceI_getHumidityAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Integer> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getHumidity", null, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     int ret;
                     ret = istr.readInt();
                     return ret;
                 });
        return f;
    }

    default byte[] getPhoto()
    {
        return getPhoto(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default byte[] getPhoto(java.util.Map<String, String> context)
    {
        return _iceI_getPhotoAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<byte[]> getPhotoAsync()
    {
        return _iceI_getPhotoAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<byte[]> getPhotoAsync(java.util.Map<String, String> context)
    {
        return _iceI_getPhotoAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<byte[]> _iceI_getPhotoAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<byte[]> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getPhoto", null, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     byte[] ret;
                     ret = istr.readByteSeq();
                     return ret;
                 });
        return f;
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static FrigdePrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), FrigdePrx.class, _FrigdePrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static FrigdePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), FrigdePrx.class, _FrigdePrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static FrigdePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), FrigdePrx.class, _FrigdePrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static FrigdePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), FrigdePrx.class, _FrigdePrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static FrigdePrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, FrigdePrx.class, _FrigdePrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static FrigdePrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, FrigdePrx.class, _FrigdePrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default FrigdePrx ice_context(java.util.Map<String, String> newContext)
    {
        return (FrigdePrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default FrigdePrx ice_adapterId(String newAdapterId)
    {
        return (FrigdePrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default FrigdePrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (FrigdePrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default FrigdePrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (FrigdePrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default FrigdePrx ice_invocationTimeout(int newTimeout)
    {
        return (FrigdePrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default FrigdePrx ice_connectionCached(boolean newCache)
    {
        return (FrigdePrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default FrigdePrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (FrigdePrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default FrigdePrx ice_secure(boolean b)
    {
        return (FrigdePrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default FrigdePrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (FrigdePrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default FrigdePrx ice_preferSecure(boolean b)
    {
        return (FrigdePrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default FrigdePrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (FrigdePrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default FrigdePrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (FrigdePrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default FrigdePrx ice_collocationOptimized(boolean b)
    {
        return (FrigdePrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default FrigdePrx ice_twoway()
    {
        return (FrigdePrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default FrigdePrx ice_oneway()
    {
        return (FrigdePrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default FrigdePrx ice_batchOneway()
    {
        return (FrigdePrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default FrigdePrx ice_datagram()
    {
        return (FrigdePrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default FrigdePrx ice_batchDatagram()
    {
        return (FrigdePrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default FrigdePrx ice_compress(boolean co)
    {
        return (FrigdePrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default FrigdePrx ice_timeout(int t)
    {
        return (FrigdePrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default FrigdePrx ice_connectionId(String connectionId)
    {
        return (FrigdePrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default FrigdePrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (FrigdePrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::Smart::Frigde";
    }
}
