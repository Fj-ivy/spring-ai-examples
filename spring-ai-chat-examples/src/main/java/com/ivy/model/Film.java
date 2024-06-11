package com.ivy.model;

/**
 * 电影返回对象
 *
 * @param director
 * @param filmName
 * @param publishedDate
 * @param description
 */
public record Film(String director, String filmName, String publishedDate, String description) {
}
