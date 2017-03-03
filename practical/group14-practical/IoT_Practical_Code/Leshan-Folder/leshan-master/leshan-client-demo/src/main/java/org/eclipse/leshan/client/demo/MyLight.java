/*******************************************************************************
 * Copyright (c) 2017 Sierra Wireless and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *     Sierra Wireless - initial API and implementation
 *******************************************************************************/
package org.eclipse.leshan.client.demo;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLight extends BaseInstanceEnabler {

    private static final Logger LOG = LoggerFactory.getLogger(MyLocation.class);
    private String LightState;
    private String UserType;
    private String UserID;
    private String LightColor;
	private boolean lowLight;
    private long groupNo;
    private double locationX;
    private double locationY;
	private String roomID;
    private String BehaviorDevelopment;
    private String ownershipPriority;
	private String lightBehavior;

    public MyLight() {
        this.UserID = "Office-Worker-" + getGroupNo();
    	this.UserType = "USER";
        this.LightState = "USED"; // "FREE"
    	this.LightColor = "47.135.69";
        this.lowLight = true;
        this.groupNo = 0;
        this.locationX = 0;
        this.locationY = 0;
        this.roomID = "Room-0";
        this.BehaviorDevelopment = "Broker";
    }

    @Override
    public ReadResponse read(int resourceid) {
        LOG.info("Read on Location Resource " + resourceid);
        switch (resourceid) {
        case 0:
            return ReadResponse.success(resourceid, getLightID());
        case 1:
            return ReadResponse.success(resourceid, getDeviceType());
        case 2:
            return ReadResponse.success(resourceid, getLightState());
        case 3:
            return ReadResponse.success(resourceid, getUserType());
        case 4:
            return ReadResponse.success(resourceid, getUserID());
        case 5:
            return ReadResponse.success(resourceid, getLightColor());
        case 6:
            return ReadResponse.success(resourceid, getLowLight());
        case 7:
            return ReadResponse.success(resourceid, getGroupNo());
        case 8:
            return ReadResponse.success(resourceid, getLocationX());
        case 9:
            return ReadResponse.success(resourceid, getLocationY());
        case 10:
            return ReadResponse.success(resourceid, getRoomID());
        case 11:
            return ReadResponse.success(resourceid, getBehaviorDeployment());
        case 12:
            return ReadResponse.success(resourceid, ownershipPriority);
        case 13:
            return ReadResponse.success(resourceid, lightBehavior);

        default:
            return super.read(resourceid);
        }
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        LOG.info("Write on Device Resource " + resourceid + " value " + value);
        switch (resourceid) {
        case 2:
            setLightState((String) value.getValue());
            fireResourcesChange(resourceid);
            return WriteResponse.success();
        case 3:
            setUserType((String) value.getValue());
            fireResourcesChange(resourceid);
            return WriteResponse.success();
        case 4:
            setUserID((String) value.getValue());
            fireResourcesChange(resourceid);
            return WriteResponse.success();
        case 5:
            setLightColor((String) value.getValue());
            fireResourcesChange(resourceid);
            return WriteResponse.success();
        case 6:
            setLowLight((boolean) value.getValue());
            fireResourcesChange(resourceid);
            return WriteResponse.success();      
        case 7:
            setGroupNo((long) value.getValue());
            fireResourcesChange(resourceid);
            return WriteResponse.success();
        case 8:
            setLocationX((double) value.getValue());
            fireResourcesChange(resourceid);
            return WriteResponse.success();
        case 9:
            setLocationY((double) value.getValue());
            fireResourcesChange(resourceid);
            return WriteResponse.success();
        case 10:
            setRoomID((String) value.getValue());
            fireResourcesChange(resourceid);
            return WriteResponse.success();
        case 11:
        	setBehaviorDevelopment((String) value.getValue());
            fireResourcesChange(resourceid);
            return WriteResponse.success();
        case 12:
            setOwnershipPriority((String) value.getValue());
            fireResourcesChange(resourceid);
            return WriteResponse.success();
        case 13:
            setLightBehavior((String) value.getValue());
            fireResourcesChange(resourceid);
            return WriteResponse.success();   
        default:
            return super.write(resourceid, value);
        }
    }

    // getters
    private String getLightID() {
        return "Light-Device-" + getGroupNo() + "-1";
    }

    private String getDeviceType() {
        return "Light Device";
    }

    private String getLightState() {
        return LightState; // "FREE" or "USED"
    }

    private String getUserType() {
    	return UserType; // "USER1", "USER2" or "USER3"
    }
    
    private String getUserID() {
        return UserID;
    }

    private String getLightColor() {
        return LightColor;
    }
    
    private boolean getLowLight() {
        return lowLight; // true, false
    }
    
    private long getGroupNo() {
        return groupNo;
    }
    
    private double getLocationX() {
        return locationX;
    }

    private double getLocationY() {
        return locationY;
    }

    private String getRoomID() {
        return roomID;
    }
    
    private String getBehaviorDeployment() {
        return BehaviorDevelopment; // Distributed
    }
    
    // Setters    
    private void setLightState(String state) {
    	LightState = state;
    }

    private void setUserType(String type) {
    	UserType = type;
    }
    
    private void setUserID(String userid) {
    	UserID = "Office-Worker-" + userid;
    }
    
    public void setLightColor(String color) {
    	LightColor = color;
    }

    public void setLowLight(boolean low) {
    	lowLight = low;
    }
    
    private void setGroupNo(long group) {
        groupNo = group;
    }

    private void setLocationX(double x) {
        locationX = x;
    }

    private void setLocationY(double y) {
        locationY = y;
    }

    private void setRoomID(String room) {
        roomID = room;
    }
    
    private void setBehaviorDevelopment(String behavior) {
    	BehaviorDevelopment = behavior;
    }
    
    private void setOwnershipPriority(String prio) {
        ownershipPriority = prio;
    }
    
    private void setLightBehavior(String lightB) {
        lightBehavior = lightB;
    }    
}