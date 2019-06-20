package com.driima.foxen;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
final class CommandProfile {
    private final Command annotation;
    private final MethodProfile methodProfile;
    private final UsageProfile usageProfile;
    private final CommandExecutor executor;
}
