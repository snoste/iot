avahi-browse -rt _coap._udp +
eth1 IPv6 light _coap._udp local +
eth1 IPv4 light _coap._udp local +
eth0 IPv6 light _coap._udp local +
eth0 IPv4 light _coap._udp local =
eth1 IPv6 light _coap._udp local
hostname = [instant-contiki.local]
address = [fe80::20c:29ff:fe79:e439]
port = [5683]
txt = ["“/mylight”"]
= eth1 IPv4 light _coap._udp local
hostname = [instant-contiki.local]
address = [192.168.2.64]
port = [5683]
txt = ["“/mylight”"]
= eth0 IPv6 light _coap._udp local
hostname = [instant-contiki.local]
address = [fe80::20c:29ff:fe79:e42f]
port = [5683]
txt = ["“/mylight”"]
= eth0 IPv4 light _coap._udp local
hostname = [instant-contiki.local]
address = [192.168.10.130]
port = [5683]
txt = ["“/mylight”"]
