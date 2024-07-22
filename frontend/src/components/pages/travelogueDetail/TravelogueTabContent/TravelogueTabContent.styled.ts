import styled from "@emotion/styled";

export const BoxContainer = styled.div`
  display: flex;
  margin: 3.2rem 0;
  padding: 0 1.6rem;
  flex-direction: column;
  gap: 0.8rem;
`;

export const Title = styled.span`
  ${({ theme }) => theme.typography.mobile.title}
  margin-left: 1.6rem;
`;