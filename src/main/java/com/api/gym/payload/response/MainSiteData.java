package com.api.gym.payload.response;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class MainSiteData
{
     Integer newUsers;
     Integer numberOfTrainingSession;
}
