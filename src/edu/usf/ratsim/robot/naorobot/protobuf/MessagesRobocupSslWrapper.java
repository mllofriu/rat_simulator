// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: messages_robocup_ssl_wrapper.proto

package edu.usf.ratsim.robot.naorobot.protobuf;

public final class MessagesRobocupSslWrapper {
  private MessagesRobocupSslWrapper() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface SSL_WrapperPacketOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // optional .SSL_DetectionFrame detection = 1;
    /**
     * <code>optional .SSL_DetectionFrame detection = 1;</code>
     */
    boolean hasDetection();
    /**
     * <code>optional .SSL_DetectionFrame detection = 1;</code>
     */
    edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame getDetection();
    /**
     * <code>optional .SSL_DetectionFrame detection = 1;</code>
     */
    edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrameOrBuilder getDetectionOrBuilder();

    // optional .SSL_GeometryData geometry = 2;
    /**
     * <code>optional .SSL_GeometryData geometry = 2;</code>
     */
    boolean hasGeometry();
    /**
     * <code>optional .SSL_GeometryData geometry = 2;</code>
     */
    edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData getGeometry();
    /**
     * <code>optional .SSL_GeometryData geometry = 2;</code>
     */
    edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryDataOrBuilder getGeometryOrBuilder();
  }
  /**
   * Protobuf type {@code SSL_WrapperPacket}
   */
  public static final class SSL_WrapperPacket extends
      com.google.protobuf.GeneratedMessage
      implements SSL_WrapperPacketOrBuilder {
    // Use SSL_WrapperPacket.newBuilder() to construct.
    private SSL_WrapperPacket(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private SSL_WrapperPacket(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final SSL_WrapperPacket defaultInstance;
    public static SSL_WrapperPacket getDefaultInstance() {
      return defaultInstance;
    }

    public SSL_WrapperPacket getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private SSL_WrapperPacket(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.Builder subBuilder = null;
              if (((bitField0_ & 0x00000001) == 0x00000001)) {
                subBuilder = detection_.toBuilder();
              }
              detection_ = input.readMessage(edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.PARSER, extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(detection_);
                detection_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000001;
              break;
            }
            case 18: {
              edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.Builder subBuilder = null;
              if (((bitField0_ & 0x00000002) == 0x00000002)) {
                subBuilder = geometry_.toBuilder();
              }
              geometry_ = input.readMessage(edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.PARSER, extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(geometry_);
                geometry_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000002;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.internal_static_SSL_WrapperPacket_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.internal_static_SSL_WrapperPacket_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket.class, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket.Builder.class);
    }

    public static com.google.protobuf.Parser<SSL_WrapperPacket> PARSER =
        new com.google.protobuf.AbstractParser<SSL_WrapperPacket>() {
      public SSL_WrapperPacket parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new SSL_WrapperPacket(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<SSL_WrapperPacket> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // optional .SSL_DetectionFrame detection = 1;
    public static final int DETECTION_FIELD_NUMBER = 1;
    private edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame detection_;
    /**
     * <code>optional .SSL_DetectionFrame detection = 1;</code>
     */
    public boolean hasDetection() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional .SSL_DetectionFrame detection = 1;</code>
     */
    public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame getDetection() {
      return detection_;
    }
    /**
     * <code>optional .SSL_DetectionFrame detection = 1;</code>
     */
    public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrameOrBuilder getDetectionOrBuilder() {
      return detection_;
    }

    // optional .SSL_GeometryData geometry = 2;
    public static final int GEOMETRY_FIELD_NUMBER = 2;
    private edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData geometry_;
    /**
     * <code>optional .SSL_GeometryData geometry = 2;</code>
     */
    public boolean hasGeometry() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional .SSL_GeometryData geometry = 2;</code>
     */
    public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData getGeometry() {
      return geometry_;
    }
    /**
     * <code>optional .SSL_GeometryData geometry = 2;</code>
     */
    public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryDataOrBuilder getGeometryOrBuilder() {
      return geometry_;
    }

    private void initFields() {
      detection_ = edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.getDefaultInstance();
      geometry_ = edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.getDefaultInstance();
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (hasDetection()) {
        if (!getDetection().isInitialized()) {
          memoizedIsInitialized = 0;
          return false;
        }
      }
      if (hasGeometry()) {
        if (!getGeometry().isInitialized()) {
          memoizedIsInitialized = 0;
          return false;
        }
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeMessage(1, detection_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeMessage(2, geometry_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, detection_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, geometry_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code SSL_WrapperPacket}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacketOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.internal_static_SSL_WrapperPacket_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.internal_static_SSL_WrapperPacket_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket.class, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket.Builder.class);
      }

      // Construct using edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
          getDetectionFieldBuilder();
          getGeometryFieldBuilder();
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        if (detectionBuilder_ == null) {
          detection_ = edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.getDefaultInstance();
        } else {
          detectionBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        if (geometryBuilder_ == null) {
          geometry_ = edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.getDefaultInstance();
        } else {
          geometryBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.internal_static_SSL_WrapperPacket_descriptor;
      }

      public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket getDefaultInstanceForType() {
        return edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket.getDefaultInstance();
      }

      public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket build() {
        edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket buildPartial() {
        edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket result = new edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        if (detectionBuilder_ == null) {
          result.detection_ = detection_;
        } else {
          result.detection_ = detectionBuilder_.build();
        }
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        if (geometryBuilder_ == null) {
          result.geometry_ = geometry_;
        } else {
          result.geometry_ = geometryBuilder_.build();
        }
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket) {
          return mergeFrom((edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket other) {
        if (other == edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket.getDefaultInstance()) return this;
        if (other.hasDetection()) {
          mergeDetection(other.getDetection());
        }
        if (other.hasGeometry()) {
          mergeGeometry(other.getGeometry());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (hasDetection()) {
          if (!getDetection().isInitialized()) {
            
            return false;
          }
        }
        if (hasGeometry()) {
          if (!getGeometry().isInitialized()) {
            
            return false;
          }
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // optional .SSL_DetectionFrame detection = 1;
      private edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame detection_ = edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.getDefaultInstance();
      private com.google.protobuf.SingleFieldBuilder<
          edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.Builder, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrameOrBuilder> detectionBuilder_;
      /**
       * <code>optional .SSL_DetectionFrame detection = 1;</code>
       */
      public boolean hasDetection() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional .SSL_DetectionFrame detection = 1;</code>
       */
      public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame getDetection() {
        if (detectionBuilder_ == null) {
          return detection_;
        } else {
          return detectionBuilder_.getMessage();
        }
      }
      /**
       * <code>optional .SSL_DetectionFrame detection = 1;</code>
       */
      public Builder setDetection(edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame value) {
        if (detectionBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          detection_ = value;
          onChanged();
        } else {
          detectionBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>optional .SSL_DetectionFrame detection = 1;</code>
       */
      public Builder setDetection(
          edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.Builder builderForValue) {
        if (detectionBuilder_ == null) {
          detection_ = builderForValue.build();
          onChanged();
        } else {
          detectionBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>optional .SSL_DetectionFrame detection = 1;</code>
       */
      public Builder mergeDetection(edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame value) {
        if (detectionBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001) &&
              detection_ != edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.getDefaultInstance()) {
            detection_ =
              edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.newBuilder(detection_).mergeFrom(value).buildPartial();
          } else {
            detection_ = value;
          }
          onChanged();
        } else {
          detectionBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>optional .SSL_DetectionFrame detection = 1;</code>
       */
      public Builder clearDetection() {
        if (detectionBuilder_ == null) {
          detection_ = edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.getDefaultInstance();
          onChanged();
        } else {
          detectionBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      /**
       * <code>optional .SSL_DetectionFrame detection = 1;</code>
       */
      public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.Builder getDetectionBuilder() {
        bitField0_ |= 0x00000001;
        onChanged();
        return getDetectionFieldBuilder().getBuilder();
      }
      /**
       * <code>optional .SSL_DetectionFrame detection = 1;</code>
       */
      public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrameOrBuilder getDetectionOrBuilder() {
        if (detectionBuilder_ != null) {
          return detectionBuilder_.getMessageOrBuilder();
        } else {
          return detection_;
        }
      }
      /**
       * <code>optional .SSL_DetectionFrame detection = 1;</code>
       */
      private com.google.protobuf.SingleFieldBuilder<
          edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.Builder, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrameOrBuilder> 
          getDetectionFieldBuilder() {
        if (detectionBuilder_ == null) {
          detectionBuilder_ = new com.google.protobuf.SingleFieldBuilder<
              edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame.Builder, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrameOrBuilder>(
                  detection_,
                  getParentForChildren(),
                  isClean());
          detection_ = null;
        }
        return detectionBuilder_;
      }

      // optional .SSL_GeometryData geometry = 2;
      private edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData geometry_ = edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.getDefaultInstance();
      private com.google.protobuf.SingleFieldBuilder<
          edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.Builder, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryDataOrBuilder> geometryBuilder_;
      /**
       * <code>optional .SSL_GeometryData geometry = 2;</code>
       */
      public boolean hasGeometry() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional .SSL_GeometryData geometry = 2;</code>
       */
      public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData getGeometry() {
        if (geometryBuilder_ == null) {
          return geometry_;
        } else {
          return geometryBuilder_.getMessage();
        }
      }
      /**
       * <code>optional .SSL_GeometryData geometry = 2;</code>
       */
      public Builder setGeometry(edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData value) {
        if (geometryBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          geometry_ = value;
          onChanged();
        } else {
          geometryBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      /**
       * <code>optional .SSL_GeometryData geometry = 2;</code>
       */
      public Builder setGeometry(
          edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.Builder builderForValue) {
        if (geometryBuilder_ == null) {
          geometry_ = builderForValue.build();
          onChanged();
        } else {
          geometryBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      /**
       * <code>optional .SSL_GeometryData geometry = 2;</code>
       */
      public Builder mergeGeometry(edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData value) {
        if (geometryBuilder_ == null) {
          if (((bitField0_ & 0x00000002) == 0x00000002) &&
              geometry_ != edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.getDefaultInstance()) {
            geometry_ =
              edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.newBuilder(geometry_).mergeFrom(value).buildPartial();
          } else {
            geometry_ = value;
          }
          onChanged();
        } else {
          geometryBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      /**
       * <code>optional .SSL_GeometryData geometry = 2;</code>
       */
      public Builder clearGeometry() {
        if (geometryBuilder_ == null) {
          geometry_ = edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.getDefaultInstance();
          onChanged();
        } else {
          geometryBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }
      /**
       * <code>optional .SSL_GeometryData geometry = 2;</code>
       */
      public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.Builder getGeometryBuilder() {
        bitField0_ |= 0x00000002;
        onChanged();
        return getGeometryFieldBuilder().getBuilder();
      }
      /**
       * <code>optional .SSL_GeometryData geometry = 2;</code>
       */
      public edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryDataOrBuilder getGeometryOrBuilder() {
        if (geometryBuilder_ != null) {
          return geometryBuilder_.getMessageOrBuilder();
        } else {
          return geometry_;
        }
      }
      /**
       * <code>optional .SSL_GeometryData geometry = 2;</code>
       */
      private com.google.protobuf.SingleFieldBuilder<
          edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.Builder, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryDataOrBuilder> 
          getGeometryFieldBuilder() {
        if (geometryBuilder_ == null) {
          geometryBuilder_ = new com.google.protobuf.SingleFieldBuilder<
              edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryData.Builder, edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.SSL_GeometryDataOrBuilder>(
                  geometry_,
                  getParentForChildren(),
                  isClean());
          geometry_ = null;
        }
        return geometryBuilder_;
      }

      // @@protoc_insertion_point(builder_scope:SSL_WrapperPacket)
    }

    static {
      defaultInstance = new SSL_WrapperPacket(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:SSL_WrapperPacket)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_SSL_WrapperPacket_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_SSL_WrapperPacket_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\"messages_robocup_ssl_wrapper.proto\032$me" +
      "ssages_robocup_ssl_detection.proto\032#mess" +
      "ages_robocup_ssl_geometry.proto\"`\n\021SSL_W" +
      "rapperPacket\022&\n\tdetection\030\001 \001(\0132\023.SSL_De" +
      "tectionFrame\022#\n\010geometry\030\002 \001(\0132\021.SSL_Geo" +
      "metryDataB(\n&edu.usf.ratsim.robot.naorob" +
      "ot.protobuf"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_SSL_WrapperPacket_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_SSL_WrapperPacket_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_SSL_WrapperPacket_descriptor,
              new java.lang.String[] { "Detection", "Geometry", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.getDescriptor(),
          edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslGeometry.getDescriptor(),
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}