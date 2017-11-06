package com.virginvoyages.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.Link;

import java.util.Objects;

/**
 * Links
 */

public class Links {
    @JsonProperty("first")
    private Link first = null;

    @JsonProperty("prev")
    private Link prev = null;

    @JsonProperty("self")
    private Link self = null;

    @JsonProperty("next")
    private Link next = null;

    @JsonProperty("last")
    private Link last = null;

    @JsonProperty("profile")
    private Link profile = null;

    public Links first(Link first) {
        this.first = first;
        return this;
    }

    /**
     * Get first
     *
     * @return first
     **/
    @ApiModelProperty(value = "")
    public Link getFirst() {
        return first;
    }

    public void link(Link first) {
        this.first = first;
    }

    public Links prev(Link prev) {
        this.prev = prev;
        return this;
    }

    /**
     * Get prev
     *
     * @return prev
     **/
    @ApiModelProperty(value = "")
    public Link getPrev() {
        return prev;
    }

    public void setPrev(Link prev) {
        this.prev = prev;
    }

    public Links self(Link self) {
        this.self = self;
        return this;
    }

    /**
     * Get self
     *
     * @return self
     **/
    @ApiModelProperty(value = "")
    public Link getSelf() {
        return self;
    }

    public void setSelf(Link self) {
        this.self = self;
    }

    public Links next(Link next) {
        this.next = next;
        return this;
    }

    /**
     * Get next
     *
     * @return next
     **/
    @ApiModelProperty(value = "")
    public Link getNext() {
        return next;
    }

    public void setNext(Link next) {
        this.next = next;
    }

    public Links last(Link last) {
        this.last = last;
        return this;
    }

    /**
     * Get last
     *
     * @return last
     **/
    @ApiModelProperty(value = "")
    public Link getLast() {
        return last;
    }

    public void setLast(Link last) {
        this.last = last;
    }

    public Links profile(Link profile) {
        this.profile = profile;
        return this;
    }

    /**
     * Get profile
     *
     * @return profile
     **/
    @ApiModelProperty(value = "")
    public Link getProfile() {
        return profile;
    }

    public void setProfile(Link profile) {
        this.profile = profile;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Links links = (Links) o;
        return Objects.equals(this.first, links.first) &&
                Objects.equals(this.prev, links.prev) &&
                Objects.equals(this.self, links.self) &&
                Objects.equals(this.next, links.next) &&
                Objects.equals(this.last, links.last) &&
                Objects.equals(this.profile, links.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, prev, self, next, last, profile);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Links {\n");

        sb.append("    first: ").append(toIndentedString(first)).append("\n");
        sb.append("    prev: ").append(toIndentedString(prev)).append("\n");
        sb.append("    self: ").append(toIndentedString(self)).append("\n");
        sb.append("    next: ").append(toIndentedString(next)).append("\n");
        sb.append("    last: ").append(toIndentedString(last)).append("\n");
        sb.append("    profile: ").append(toIndentedString(profile)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

