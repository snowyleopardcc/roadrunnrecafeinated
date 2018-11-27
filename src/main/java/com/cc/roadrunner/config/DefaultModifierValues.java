package com.cc.roadrunner.config;

enum DefaultModifierValues {
	
	
	ATTACK( 4.0f, 64.0f),
	KNOCKBACK(0.0f, 1.0f),
	SPEED(0.7f, 64.0f);
	
	
	private final float defaultValue;
	private final float maxValue;
	
	private DefaultModifierValues(float defaultValue, float maxValue) {
		
		this.defaultValue = defaultValue;
		this.maxValue = maxValue;
	}
	
	public float getDefaultValue() { return this.defaultValue; }
	public float getMaxValue() { return this.maxValue; }
	
}
